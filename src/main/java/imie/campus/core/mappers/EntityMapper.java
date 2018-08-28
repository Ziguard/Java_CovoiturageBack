package imie.campus.core.mappers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Describe the behaviour of an entity mapper, used by the REST facade controllers,
 * and which is responsible for the mapping of a S typed object to a T typed object.
 * An EntityMapper is basically used to map a persistence entity to the corresponding
 * transfert object, according to the DTO pattern.
 * @param <S> The type of the source entity
 * @param <T> The type of the target entity
 */
public interface EntityMapper<S, T> {
    /**
     * Map a source object to a target object.
     * @param source The source object
     * @return The target mapped object
     */
    T map(S source);

    /**
     * Reverse map a target object to a soruce object.
     * @param target The target object
     * @return The source reversely mapped object
     */
    S reverse(T target);

    /**
     * Map a collection of source objects to a collection of target objects.
     * @param source The source collection
     * @return A collection of all mapped target objects
     */
    default List<T> map(Iterable<S> source) {
        return StreamSupport.stream(source.spliterator(), false)
                .map(this::map)
                .collect(Collectors.toList());
    }

    /**
     * Reversely map a collection of target objects to a collection of source objects.
     * @param target The collection of target objects
     * @return A collection of all mapped source objects
     */
    default List<S> reverse(Iterable<T> target) {
        return StreamSupport.stream(target.spliterator(), false)
                .map(this::reverse)
                .collect(Collectors.toList());
    }
}
