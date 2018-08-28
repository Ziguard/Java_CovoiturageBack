package imie.campus.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A utility builder to creating quickly and in a dot-chained way instances of Map.
 * @see Map
 * @author Fabien
 */
public class PropertyBuilder {

    /**
     * The current map to build
     */
    private final Map<String, Object> map;

    /**
     * Creation constructor
     */
    private PropertyBuilder() {
        this.map = new HashMap<>();
    }

    /**
     * Add key/value tuples from the provided iterables
     * If both collections does not have the same size, this method will stop
     *   at the end of the smallest one.
     * @param keys An iterable of String keys
     * @param values An iterable of objects to use a values
     * @return The current builder
     */
    public PropertyBuilder put(Iterable<String> keys, Iterable<Object> values) {
        Iterator<String> k = keys.iterator();
        Iterator<Object> v = values.iterator();

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
    public PropertyBuilder put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    /**
     * Build the map.
     * @return A Map instance
     */
    public Map<String, Object> build() {
        return build(true);
    }

    /**
     * Build the map.
     * @param dropNull drops all tuple with null value if true
     * @return A Map instance
     */
    public Map<String, Object> build(boolean dropNull) {
        return map.entrySet().stream()
                .filter(e -> (!dropNull) || e.getValue() != null)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Returns a new MapBulder
     * @return A parametized <K, V> MapBuilder instance
     */
    public static PropertyBuilder map() {
        return new imie.campus.utils.PropertyBuilder();
    }

    /**
     * Returns a MapBuilder for properties (string key, object value) map building
     * @return A parametized <String, Object> MapBuilder instance
     */
    public static PropertyBuilder properties() {
        return new PropertyBuilder();
    }

    /**
     * Returns a MapBuilder from an existing map.
     * @param originalMap The base map for the builder
     * @return A parametized <K, V> MapBuilder instance filled with the original map
     */
    public static PropertyBuilder from(Map<String, Object> originalMap) {
        return PropertyBuilder.map()
            .put(originalMap.keySet(), originalMap.values());
    }
}
