package imie.campus.utils.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A general purpose functions librairies for the application.
 * @author Fabien
 */
public class GeneralUtils {

    final static String FORMAT_TOKEN = "{}";

    /**
     * Indicates whether all the objects in the provided sequence are non nulls.
     * @param objects An objects sequence
     * @return true if and only if all the objects are not nulls
     */
    public static boolean nonNulls(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indicates whether none of the provided string are empty.
     * @param strings A string sequence
     * @return true if and only if all the strings are different of null and not empty
     */
    public static boolean nonEmpty(String... strings) {
        for (String str : strings) {
            if (str == null || str.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Convert the object to JSON.
     * @param obj The object to convert
     * @return The object JSON representation, or {} if an error has occurred
     */
    public static String toJson(Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    /**
     * Indicates whether a string is null or empty (without trailing spaces).
     * @param string The stirng to check
     * @return true si the string is null or empty or contains only whitespaces.
     */
    public static boolean isEmpty(final String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * Indicates whether the provided array is null or empty.
     * @param array An object array
     * @return <code>true</code> if the array is null, or empty
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * Indicates whether the provided Iterable is null or empty.
     * @param items An instance of Iterable<E>
     * @param <E> The type of elements
     * @return true if iterable is null or has no elements.
     */
    public static <E> boolean isEmpty(Iterable<E> items) {
        return items == null || !items.iterator().hasNext();
    }

    /**
     * Convert a ZonedDateTime instance to a legacy Date.
     * @param local A datetime associated with a timezone
     * @see ZonedDateTime
     * @return A java.util.Date instance
     */
    public static Date toDate(final ZonedDateTime local) {
        return Date.from(local.toInstant());
    }

    public static ZonedDateTime fromDate(final Date date) {
        return date.toInstant().atZone(ZoneId.of("UTC"));
    }

    /**
     * Format a string, replacing in arguments order, occurences of {}.
     * Exemples :
     *   format("Je m'appelle {} et j'ai {} ans, "Fabien", 26) => "Je m'appelle Fabien et j'ai 26 ans"
     *   format("Je m'appelle {} et j'ai {} ans, "Fabien") => "Je m'appelle Fabien et j'ai {} ans"
     *   format("{} + {} = {}", 2, 2, 4) => "2 + 2 = 4"
     *   format("{} + {} = {} ", 2, 2, 4, 8) => "2 + 2 = 4"
     *   format("Bonjour !") => "Bonjour !"
     *
     * @param format The format string
     * @param args The objects to format
     * @return The formatted string
     */
    public static String format(final String format, Object... args) {
        final String regex = Pattern.quote(FORMAT_TOKEN);
        Iterator<Object> iterator = Arrays.asList(args).iterator();
        String result = format;

        while (iterator.hasNext() && result.contains(FORMAT_TOKEN)) {
            final String repr = iterator.next().toString();
            result = result.replaceFirst(regex, repr);
        }

        return result;
    }

    /**
     * Transforms a string from camelCase to UPPERCASE_UNDERSCORE
     * @param source The string to convert
     * @return The conversion result
     */
    public static String camelCaseToUpperCaseUnderscore(String source) {
        final Pattern p = Pattern.compile("(([A-Z])([a-z]+)?)");
        final Matcher m = p.matcher(source);
        final List<String> parts = new ArrayList<>();

        while (m.find()) {
            parts.add(m.group(1).toUpperCase());
        }

        return String.join("_", parts);
    }

    /**
     * Returns the first non null element amoung provided items array.
     * @param items The array of items
     * @param <T> The type of the element
     * @return The first non null element, or null if all items are null
     */
    @SafeVarargs
    public static <T> T firstDefinedAmong(T... items) {
        return Stream.of(items)
            .filter(Objects::nonNull)
            .findFirst()
            .orElse(null);
    }

    /**
     * Returns the primitive boolean value from the Boolean wrapper class.
     * @param wrapper The Boolean wrapper
     * @return false if wrapper is null or its value is false, true else
     */
    public static boolean booleanValue(Boolean wrapper) {
        return wrapper != null && wrapper;
    }

    /**
     * Get a throwable stacktrace as a string.
     * @param t The throwable
     * @return The stacktrace as a string
     */
    public static String getStackTraceAsString(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Finds the last exception of an exception cause hierarchy.
     * @param ex The exception
     * @return The last exception instance
     */
    public static Throwable findLastExceptionInHierarchy(Throwable ex) {
        Set<Throwable> proceeded = new HashSet<>();
        Throwable current = ex;

        while (current.getCause() != null && proceeded.add(current)) {
            current = current.getCause();
        }

        return current;
    }

    /**
     * Gets the value of an enum constant from its name.
     * @param enumType The class of the Enum type
     * @param name The constant name
     * @param <T> The type of Enum
     * @return The found constant, or null if none constants with this name was found
     */
    public static <T extends Enum<T>> T valueOfEnum(Class<T> enumType, String name) {
        try {
            return T.valueOf(enumType, name);
        } catch (IllegalArgumentException | NullPointerException ex) {
            return null;
        }
    }
}
