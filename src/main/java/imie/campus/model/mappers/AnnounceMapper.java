package imie.campus.model.mappers;

import imie.campus.core.mappers.AbstractEntityMapper;
import imie.campus.model.dto.AnnounceTO;
import imie.campus.model.entities.Announce;

import static imie.campus.core.mappers.converters.Converters.mapper;
import static imie.campus.core.mappers.converters.StandardConverters.dateFromString;
import static imie.campus.core.mappers.converters.StandardConverters.dateToString;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public abstract class AnnounceMapper<S extends Announce, T extends AnnounceTO> extends AbstractEntityMapper<S, T> {

    protected final UserMapper userMapper;

    protected AnnounceMapper(Class<S> sourceClass, Class<T> targetClass,
                             UserMapper userMapper) {
        super(sourceClass, targetClass);
        this.userMapper = userMapper;
    }

    @Override
    public void configure() {
        defaultTypeMap().addMappings(mappings -> {
            mappings.map(Announce::getId, AnnounceTO::setId);
            mappings.using(mapper(userMapper)).map(Announce::getOwner, AnnounceTO::setOwner);
            mappings.using(dateToString(ISO_DATE_TIME)).map(Announce::getCreationDate, AnnounceTO::setCreationDate);
        });
        reverseTypeMap().addMappings(mapper -> {
            mapper.map(AnnounceTO::getId, Announce::setId);
            mapper.using(dateFromString(ISO_DATE_TIME)).map(AnnounceTO::getCreationDate, Announce::setCreationDate);
        });
    }
}
