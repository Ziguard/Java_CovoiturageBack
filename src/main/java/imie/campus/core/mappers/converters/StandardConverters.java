package imie.campus.core.mappers.converters;

import imie.campus.core.entities.BaseEntity;
import imie.campus.core.services.AbstractEntityService;
import imie.campus.utils.commons.GeneralUtils;
import org.modelmapper.Converter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An utility and factory class for creating Converter instances for standard data types conversion,
 * like strings, date, etc.
 * @see org.modelmapper.Converter
 * @author Fabien
 */
public class StandardConverters {

    /**
     * Returns a Converter that transforms an Entity to its primary key.
     * @param <E> The type of the entity
     * @param <ID> The type of the entity primary key
     * @return A Converter<E, ID> instance
     */
    public static <E extends BaseEntity<ID>, ID extends Serializable>
    Converter<E, ID> toPrimaryKey() {
        return Converters.function(BaseEntity::primaryKey);
    }

    /**
     * Returns a Converter that attempts to find an Entity from its primary key.
     * @param entityService The corresponding entityService
     * @param <E> The type of the entity
     * @param <ID> The type of the entity primary key
     * @return A Converter<ID, E> instance
     */
    public static <E extends BaseEntity<ID>, ID extends Serializable>
    Converter<ID, E> fromPrimaryKey(AbstractEntityService<E, ID> entityService) {
        return Converters.function(entityService::find);
    }

    /**
     * Returns a Converter instance that converts a LocalDateTime to a
     *   string representation, according to the given formatter.
     * @param formatter A DateTimeFormatter instance that describes the date format
     * @return A Converter<LocalDateTime, String> instance
     */
    public static Converter<LocalDateTime, String>
    dateToString(DateTimeFormatter formatter) {
        return Converters.function(formatter::format);
    }

    /**
     * Returns a Converter instance that converts a string representation of a
     *   date to a LocalDateTime, according to the given formatter.
     * @param formatter A DateTimeFormatter instance that describes the date format
     * @return A Converter<String, LocalDateTime> instance
     */
    public static Converter<String, LocalDateTime>
    dateFromString(DateTimeFormatter formatter) {
        return Converters.function(s -> LocalDateTime.parse(s, formatter));
    }

    /**
     * Returns a Converter instance that converts an enum constant to a string
     *   representation of its name.
     * @return A Converter<Enum, String> instance
     */
    public static Converter<Enum, String> enumToString() {
        return Converters.function(Enum::name);
    }

    /**
     * Returns a Converter instance that try to convert a string representation of
     *   an the enumClass typed constant to the matching constant, if exists.
     * @param enumClass The class of the enum
     * @param <T> The type of the enum
     * @return A Converter<String, T> instance
     */
    public static <T extends Enum<T>> Converter<String, T>
    enumFromString(Class<T> enumClass) {
        return enumFromString(enumClass, null);
    }

    /**
     * Returns a Converter instance that try to convert a string representation of
     *   an the enumClass typed constant to the matching constant, if exists.
     * @param enumClass The class of the enum
     * @param defaultEnumConstant The default enum constant value
     * @param <T> The type of the enum
     * @return A Converter<String, T> instance
     */
    public static <T extends Enum<T>> Converter<String, T>
    enumFromString(Class<T> enumClass,
                   T defaultEnumConstant) {
        return Converters.function(name -> {
            T enumValue = GeneralUtils.valueOfEnum(enumClass, name);
            return (enumValue != null) ? enumValue : defaultEnumConstant;
        });
    }
}
