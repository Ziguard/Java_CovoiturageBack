package imie.campus.model.mappers;

import imie.campus.core.mappers.AbstractEntityMapper;
import imie.campus.dao.CarpoolingService;
import imie.campus.model.dto.LagTO;
import imie.campus.model.entities.Lag;
import imie.campus.model.enums.LagCauseEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static imie.campus.core.mappers.converters.StandardConverters.*;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Service
public class LagMapper extends AbstractEntityMapper<Lag, LagTO> {

    private final CarpoolingService carpoolingService;

    @Autowired
    public LagMapper(CarpoolingService carpoolingService) {
        super(Lag.class, LagTO.class);
        this.carpoolingService = carpoolingService;
    }

    @Override
    public void configure() {
        defaultTypeMap().addMappings(mapping -> {
            mapping.using(toPrimaryKey()).map(Lag::getCarpooling, LagTO::setCarpoolingId);
            mapping.using(enumToString()).map(Lag::getCause, LagTO::setCause);
            mapping.using(dateToString(ISO_DATE_TIME)).map(Lag::getDate, LagTO::setDate);
        });
        reverseTypeMap().addMappings(mapping -> {
            mapping.using(fromPrimaryKey(carpoolingService)).map(LagTO::getCarpoolingId, Lag::setCarpooling);
            mapping.using(enumFromString(LagCauseEnum.class)).map(LagTO::getCause, Lag::setCause);
            mapping.using(dateFromString(ISO_DATE_TIME)).map(LagTO::getDate, Lag::setDate);
        });
    }
}
