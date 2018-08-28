package imie.campus.utils.rest;

import imie.campus.core.entities.BaseEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static imie.campus.utils.commons.ReflectionUtils.hasAnnotations;

/**
 * An utility class for the AbstractEntityService implementations.
 * @author Fabien
 */
public class ServiceUtils {

    /**
     * Build a Collection from an Iterable object.
     * @param iterable An iterable
     * @param factory A factory method for collection
     * @param <E> element type of the iterable
     * @param <C> The Collection<E> type
     * @return A Collection<E> instance
     */
    public static <E, C extends Collection<E>> C fromIterable(Iterable<E> iterable, Supplier<C> factory) {
        C results = factory.get();
        StreamSupport.stream(iterable.spliterator(), false)
                .forEach(results::add);
        return results;
    }

    /**
     * Build a JPA Specifications<E> object from an entity,
     *   and all its @Column(..., unique = true) properties.
     * This Specifications object can be used to find conflicting entities
     *   in the datasource.
     * @param entity The entity from which to build the criteria
     * @param <E> The type of the entity
     * @param <ID> The type of the entity primary key
     * @return A Specifications<E> instance
     */
    public static <E extends BaseEntity<ID>, ID extends Serializable>
    Specifications<E> buildUniqueSearchCriteria(E entity)
    {
        List<Specification<E>> specs =
                Stream.of(entity.getClass().getDeclaredFields())
                    .filter(ServiceUtils::isUniqueColumn)
                    .map(f -> equal(entity, f))
                .collect(Collectors.toList());

        Specifications<E> finalSpecs = null;
        if (!specs.isEmpty()) {
            finalSpecs = Specifications
                    .where(specs.remove(0));
            for (Specification<E> spec : specs)
                finalSpecs = finalSpecs.or(spec);
        }

        return finalSpecs;
    }

    private static <E extends BaseEntity<ID>, ID extends Serializable>
    Specification<E> equal(E entity, Field field) {
        return (root, query, cb) -> {
            final String propertyName = field.getName();
            return cb.equal(root.get(propertyName), getFieldValue(entity, field));
        };
    }

    private static <E extends BaseEntity<ID>, ID extends Serializable>
    Object getFieldValue(E entity, Field field) {
        Object value = null;
        try {
            field.setAccessible(true);
            value = field.get(entity);
        } catch (IllegalAccessException ignored) {
        } finally {
            field.setAccessible(false);
        }
        return value;
    }

    private static boolean isUniqueColumn(final Field field)  {
        return hasAnnotations(field, Collections.singletonList(Column.class)) &&
                !field.isAnnotationPresent(Id.class) &&
                field.getAnnotation(Column.class).unique();
    }
}
