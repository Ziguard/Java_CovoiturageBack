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
 * An utility and factory class for creating instances of Converter that reversely convert
 * an object to another.
 * Like all Convert instances crafted by Converters, all these instances are safe.
 * @see org.modelmapper.Converter
 * @see Converters
 * @author Fabien
 */
public class ReverseConverters {

    /**
     * Create a reverse Converter instance from Java 8 Function
     * @param conversionFn The function that convert target object (T) to source object (S)
     * @param <T> The type of target object
     * @param <S> The type of source object
     * @return A Converter<T, S> instance that represents the conversion of the given function
     */
    public static <T, S> Converter<T, S> reverseFunction(Function<T,S> conversionFn) {
        return reverseFunction(conversionFn, null);
    }

    /**
     * Create a reverse Converter instance from Java 8 Function
     * @param conversionFn The function that convert target object (T) to source object (S)
     * @param defaultResult The default result of the conversion
     * @param <T> The type of target object
     * @param <S> The type of source object
     * @return A Converter<T, S> instance that represents the conversion of the given function
     */
    public static <T, S> Converter<T, S> reverseFunction(Function<T,S> conversionFn, S defaultResult) {
        return ctx -> {
            if (isNull(ctx.getSource()))
                return defaultResult;

            S converted = conversionFn.apply(ctx.getSource());
            return isNull(converted) ? defaultResult : converted;
        };
    }

    /**
     * Create a reverse Converter instance from a EntityMapper.
     * @param mapper The function that convert target object (T) to source object (S)
     * @param <T> The type of target object
     * @param <S> The type of source object
     * @return A Converter<T, S> instance that represents the conversion of the given function
     */
    public static <T, S> Converter<T, S> reverseMapper(EntityMapper<S, T> mapper) {
        return reverseMapper(mapper, null);
    }

    /**
     * Create a reverse Converter instance from a EntityMapper.
     * @param mapper The function that convert target object (T) to source object (S)
     * @param defaultResult The default result of the conversion
     * @param <T> The type of target object
     * @param <S> The type of source object
     * @return A Converter<T, S> instance that represents the conversion of the given function
     */
    public static <T, S> Converter<T, S> reverseMapper(EntityMapper<S, T> mapper,
                                                       S defaultResult) {
        return reverseFunction(mapper::reverse, defaultResult);
    }

    /**
     * Create a reverse Converter instance that operates on collection of objects, from a Java 8 Function.
     * The converter will be called on each element of the collection, and the results will be
     * stored in another collection.
     * @param conversionFn The function that convert target object (T) to source object (S)
     * @param collectionFactory The function that instantiate the collection
     * @param <T> The type of target object
     * @param <S> The type of source object
     * @return A Converter<T, S> instance that represents the reverse conversion of all the collection elements
     *   from the given function
     */
    public static <T, S> Converter<Collection<T>, Collection<S>>
    reverseCollectionFunction(Function<T, S> conversionFn,
                              Supplier<? extends Collection<S>> collectionFactory) {
        return reverseCollectionFunction(conversionFn, collectionFactory, collectionFactory.get());
    }

    /**
     * Create a reverse Converter instance that operates on collection of objects, from a Java 8 Function.
     * The converter will be called on each element of the collection, and the results will be
     * stored in another collection.
     * @param conversionFn The function that convert target object (T) to source object (S)
     * @param collectionFactory The function that instantiate the collection
     * @param defaultResultList The default result list of the conversion
     * @param <T> The type of target object
     * @param <S> The type of source object
     * @return A Converter<T, S> instance that represents the reverse conversion of all the collection elements
     *   from the given function
     */
    public static <T, S> Converter<Collection<T>, Collection<S>>
    reverseCollectionFunction(Function<T, S> conversionFn,
                              Supplier<? extends Collection<S>> collectionFactory,
                              Collection<S> defaultResultList) {
        return ctx -> {
            Stream<S> outStream = nonNull(ctx.getSource()) ?
                    ctx.getSource().stream().map(conversionFn) : defaultResultList.stream();
            return outStream.collect(Collectors.toCollection(collectionFactory));
        };
    }

    /**
     * Create a reverse Converter instance that operates on collection of objects, from a EntityMapper
     * The mapper will be called on each element of the collection, and the results will be
     * stored in another collection.
     * @param mapper An instance of EntityMapper<S, T> to reversly convert T object to S object
     * @param collectionFactory The function that instantiate the collection
     * @param <T> The type of target object
     * @param <S> The type of source object
     * @return A Converter<T, S> instance that represents the reverse conversion of all the collection elements
     *   from the given function
     */
    public static <T, S> Converter<Collection<T>, Collection<S>>
    reverseCollectionMapper(EntityMapper<S, T> mapper,
                            Supplier<? extends Collection<S>> collectionFactory) {
        return reverseCollectionFunction(mapper::reverse, collectionFactory);
    }

    /**
     * Create a reverse Converter instance that operates on collection of objects, from a EntityMapper
     * The mapper will be called on each element of the collection, and the results will be
     * stored in another collection.
     * @param mapper An instance of EntityMapper<S, T> to reversly convert T object to S object
     * @param collectionFactory The function that instantiate the collection
     * @param defaultResultList The default result list
     * @param <T> The type of target object
     * @param <S> The type of source object
     * @return A Converter<T, S> instance that represents the reverse conversion of all the collection elements
     *   from the given function
     */
    public static <T, S> Converter<Collection<T>, Collection<S>>
    reverseCollectionMapper(EntityMapper<S, T> mapper,
                            Supplier<? extends Collection<S>> collectionFactory,
                            Collection<S> defaultResultList) {
        return reverseCollectionFunction(mapper::reverse, collectionFactory, defaultResultList);
    }

}
