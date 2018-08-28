package imie.campus.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * A utility builder to creating quickly and in a dot-chained way, typed <K, V> instances of Map.
 * @param <K> Key type
 * @param <V> Value type
 * @see java.util.Map
 * @author Fabien
 */
public class MapBuilder<K, V> {

    /**
     * The current map to build
     */
    private final Map<K, V> map;

    /**
     * Creation constructor
     */
    private MapBuilder() {
        this.map = new HashMap<>();
    }

    /**
     * Add key/value tuples from the provided iterables
     * If both collections does not have the same size, this method will stop
     *   at the end of the smallest one.
     * @param keys An iterable of K objects to use as keys
     * @param values An iterable of V objects to use a values
     * @return The current builder
     */
    public MapBuilder<K, V> put(Iterable<K> keys, Iterable<V> values) {
        Iterator<K> k = keys.iterator();
        Iterator<V> v = values.iterator();

        while (k.hasNext() && v.hasNext()) {
            map.put(k.next(), v.next());
        }

        return this;
    }

    /**
     * Add a key/value tuple to the current building map.
     * @param key A key
     * @param value A value
     * @return The current builder
     */
    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

    /**
     * Add key/value tuples from the two provided arrays.
     * If both arrays does not have the same size, this method will stop
     *   at the end of the smallest one.
     * @param keys An array of K objects to use as keys
     * @param values An array of V objects to use a values
     * @return The current builder
     */
    public MapBuilder<K, V> put(K[] keys, V[] values) {
        return put(Arrays.asList(keys), Arrays.asList(values));
    }

    /**
     * Add key/value tuples to the building map, from an iterable of keys
     *   and from a mapper function which will be used to determine associated value
     *   for each key.
     * @param keys An iterable of K objects as keys
     * @param mapper A mapper function to determine the value
     * @return The current builder
     */
    public MapBuilder<K, V> put(Iterable<K> keys, Function<K, V> mapper) {
        map.putAll(StreamSupport.stream(keys.spliterator(), false)
            .collect(Collectors.toMap(Function.identity(), mapper)));
        return this;
    }

    /**
     * Add key/value tuples to the building map, from an array of keys
     *   and from a mapper function which will be used to determine associated value
     *   for each key.
     * @param keys An array of K objects as keys
     * @param mapper A mapper function to determine the value
     * @return The current builder
     */
    public MapBuilder<K, V> put(K[] keys, Function<K, V> mapper) {
        return put(Arrays.asList(keys), mapper);
    }

    /**
     * Build the map.
     * @return A Map instance
     */
    public Map<K, V> build() {
        return build(true);
    }

    /**
     * Build the map.
     * @param dropNull drops all tuple with null value if true
     * @return A Map instance
     */
    public Map<K, V> build(boolean dropNull) {
        return map.entrySet().stream()
                .filter(e -> (!dropNull) || e.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Returns a new MapBulder
     * @param <K> key type
     * @param <V> value type
     * @return A parametized <K, V> MapBuilder instance
     */
    public static <K, V> MapBuilder<K, V> map() {
        return new MapBuilder<>();
    }

    /**
     * Returns a MapBuilder from an existing map.
     * @param originalMap The base map for the builder
     * @param <K> key type of the Map
     * @param <V> value type of the Map
     * @return A parametized <K, V> MapBuilder instance filled with the original map
     */
    public static <K, V> MapBuilder<K, V> from(Map<K, V> originalMap) {
        return MapBuilder.<K, V>map()
            .put(originalMap.keySet(), originalMap.values());
    }
}
