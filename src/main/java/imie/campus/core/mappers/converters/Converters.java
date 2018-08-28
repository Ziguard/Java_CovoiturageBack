package imie.campus.core.mappers.converters;

import imie.campus.core.mappers.EntityMapper;
import org.modelmapper.Converter;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * An utility and factory class for creating instances of org.modelmapper Converter class.
 * Note that all crafted converters are safes, meaning that they will be able to return
 * null or a default value if the mapping context is null.
 * @see org.modelmapper.Converter
 * @see org.modelmapper.spi.MappingContext
 * @author Fabien
 */
public class Converters {

    /**
     * Create a Converter instance from Java 8 Function
     * @param conversionFn The function that convert source object (S) to target object (T)
     * @param <S> The type of source object
     * @param <T> The type of target object
     * @return A Converter<S, T> instance that represents the conversion of the given function
     */
    public static <S, T> Converter<S, T> function(Function<S, T> conversionFn) {
        return function(conversionFn, null);
    }

    /**
     * Create a Converter instance from Java 8 Function
     * @param conversionFn The function that convert source object (S) to target object (T)
     * @param defaultResult The default result of the conversion
     * @param <S> The type of source object
     * @param <T> The type of target object
     * @return A Converter<S, T> instance that represents the conversion of the given function
     */
    public static <S, T> Converter<S, T> function(Function<S, T> conversionFn, T defaultResult) {
        return ctx -> {
            if (isNull(ctx.getSource()))
                return defaultResult;

            T converted = conversionFn.apply(ctx.getSource());
            return isNull(converted) ? defaultResult : converted;
        };
    }

    /**
     * Create a Converter instance from an EntityMapper.
     * The converter will use another EntityMapper to convert an object to another.
     * @param mapper An instance of EntityMapper<S, T> to convert S object to T object
     * @param <S> The type of source object
     * @param <T> The type of target object
     * @return A Converter<S, T> instance that represents the conversion from the given mapper
     */
    public static <S, T> Converter<S, T> mapper(EntityMapper<S, T> mapper) {
        return mapper(mapper, null);
    }

    /**
     * Create a Converter instance from an EntityMapper.
     * The converter will use another EntityMapper to convert an object to another.
     * @param mapper An instance of EntityMapper<S, T> to convert S object to T object
     * @param defaultResult The default result of the conversion
     * @param <S> The type of source object
     * @param <T> The type of target object
     * @return A Converter<S, T> instance that represents the conversion from the given mapper
     */
    public static <S, T> Converter<S, T> mapper(EntityMapper<S, T> mapper,
                                                T defaultResult) {
        return function(mapper::map, defaultResult);
    }

    /**
     * Create a Converter instance that operates on collection of objects, from a Java 8 Function.
     * The converter will be called on each element of the collection, and the results will be
     * stored in another collection.
     * @param conversionFn The function that convert source object (S) to target object (T)
     * @param collectionFactory The function that instantiate the collection
     * @param <S> The type of source object
     * @param <T> The type of target object
     * @return A Converter<S, T> instance that represents the conversion of all the collection elements
     *   from the given function
     */
    public static <S, T> Converter<Collection<S>, Collection<T>>
    collectionFunction(Function<S, T> conversionFn,
                       Supplier<? extends Collection<T>> collectionFactory) {
        return collectionFunction(conversionFn, collectionFactory, collectionFactory.get());
    }

    /**
     * Create a Converter instance that operates on collection of objects, from a Java 8 Function.
     * The converter will be called on each element of the collection, and the results will be
     * stored in another collection.
     * @param conversionFn The function that convert source object (S) to target object (T)
     * @param collectionFactory The function that instantiate the collection
     * @param defaultResultList The default collection
     * @param <S> The type of source object
     * @param <T> The type of target object
     * @return A Converter<S, T> instance that represents the conversion of all the collection elements
     *   from the given function
     */
    public static <S, T> Converter<Collection<S>, Collection<T>>
    collectionFunction(Function<S, T> conversionFn,
                       Supplier<? extends Collection<T>> collectionFactory,
                       Collection<T> defaultResultList) {
        return ctx -> {
            Stream<T> outStream = nonNull(ctx.getSource()) ?
                    ctx.getSource().stream().map(conversionFn) : defaultResultList.stream();
            return outStream.collect(Collectors.toCollection(collectionFactory));
        };
    }

    /**
     * Create a Converter instance that operates on collection of objects, from a EntityMapper
     * The mapper will be called on each element of the collection, and the results will be
     * stored in another collection.
     * @param mapper An instance of EntityMapper<S, T> to convert S object to T object
     * @param collectionFactory The function that instantiate the collection
     * @param <S> The type of source object
     * @param <T> The type of target object
     * @return A Converter<S, T> instance that represents the conversion of all the collection elements
     *   from the given function
     */
    public static <S, T> Converter<Collection<S>, Collection<T>>
    collectionMapper(EntityMapper<S, T> mapper,
                     Supplier<? extends Collection<T>> collectionFactory) {
        return collectionFunction(mapper::map, collectionFactory);
    }

    /**
     * Create a Converter instance that operates on collection of objects, from a EntityMapper
     * The mapper will be called on each element of the collection, and the results will be
     * stored in another collection.
     * @param mapper An instance of EntityMapper<S, T> to convert S object to T object
     * @param collectionFactory The function that instantiate the collection
     * @param defaultResultList The default collection
     * @param <S> The type of source object
     * @param <T> The type of target object
     * @return A Converter<S, T> instance that represents the conversion of all the collection elements
     *   from the given function
     */
    public static <S, T> Converter<Collection<S>, Collection<T>>
    collectionMapper(EntityMapper<S, T> mapper,
                     Supplier<? extends Collection<T>> collectionFactory,
                     Collection<T> defaultResultList) {
        return collectionFunction(mapper::map, collectionFactory, defaultResultList);
    }

}
