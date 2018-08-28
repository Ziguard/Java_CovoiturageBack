package imie.campus.core.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.UnaryOperator;

import static java.util.Objects.nonNull;
import static org.modelmapper.convention.MatchingStrategies.LOOSE;

/**
 * Defines the general structure of all entity mappers.
 * It implements EntityMapper to fullfill the mapping behaviour, but it also
 *   inherits from org.modelmapper ModelMapper root class, in order to extend this
 *   framework and to reduce the verbosity of declaring and instantiating a mapper.
 * @param <S> The type of the source object
 * @param <T> The type of the target object
 * @see ModelMapper
 * @see TypeMap
 * @author Fabien
 */
public abstract class AbstractEntityMapper<S, T> extends ModelMapper implements EntityMapper<S, T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The source object class
     */
    protected final Class<S> sourceClass;

    /**
     * The target object class
     */
    protected final Class<T> targetClass;

    /**
     * A callback function to alter a just mapped entity
     */
    private UnaryOperator<T> afterMap = null;

    /**
     * A callback function to alter a just reverse mapped entity
     */
    private UnaryOperator<S> afterReverse = null;

    /**
     * A flag to know if the mapper has already been configured before
     * a mapping operation.
     */
    private boolean configured = false;

    /**
     * Constructs an entity mapper, with given source and target classes.
     * @param sourceClass The source object class
     * @param targetClass The target object class
     */
    protected AbstractEntityMapper(Class<S> sourceClass, Class<T> targetClass) {
        super();
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
        getConfiguration().setMatchingStrategy(LOOSE);
    }

    /**
     * Set up the mapper before the first mapping/reverse operation.
     * Implementations must declare their configurations in this method.
     * It's there that developper can define custom mapping, especially
     * between fields that is not of the same type.
     */
    abstract public void configure();

    /**
     * Returns the underlying TypeMap object from ModelMapper.
     * Developper has to use it to add its own mapping for map() operations.
     * @return A TypeMap<S, T> instance
     */
    protected TypeMap<S, T> defaultTypeMap() {
        return this.typeMap(sourceClass, targetClass);
    }

    /**
     * Returns the underlying reverse TypeMap object from ModelMapper.
     * Developper has to use it to add its own mapping for reverse() operations.
     * @return A TypeMap<T, S> instance
     */
    protected TypeMap<T, S> reverseTypeMap() {
        return this.typeMap(targetClass, sourceClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T map(S source) {
        ensureConfigure();
        T target = super.map(source, targetClass);

        // If an afterMap callback function is defined, we call it and return the result
        return nonNull(afterMap) ? afterMap.apply(target) : target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S reverse(T target) {
        logger.debug("Attempting to reversely parse object {} of class {} to {}",
                target.toString(), targetClass.getName(), sourceClass.getName());
        ensureConfigure();
        S source = super.map(target, sourceClass);

        // If an afterReverse callback function is defined, we call it and return the result
        return nonNull(afterReverse) ? afterReverse.apply(source) : source;
    }

    /**
     * Ensures that the configuration has been set up before
     * each map() or reverse() operations.
     */
    private void ensureConfigure() {
        if (!configured) {
            configure();
            configured = true;
        }
    }

    /**
     * Set a callback function to execute after each map() operation.
     * @param afterMap A function that take a T object and return a T one
     */
    public void setAfterMap(UnaryOperator<T> afterMap) {
        this.afterMap = afterMap;
    }

    /**
     * Set a callback function to execute after each reverse() operation.
     * @param afterReverse A function that take a S object and return a S one
     */
    public void setAfterReverse(UnaryOperator<S> afterReverse) {
        this.afterReverse = afterReverse;
    }
}
