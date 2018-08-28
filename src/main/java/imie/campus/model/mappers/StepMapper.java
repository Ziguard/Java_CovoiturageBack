package imie.campus.model.mappers;

import imie.campus.core.mappers.AbstractEntityMapper;
import imie.campus.core.mappers.converters.ReverseConverters;
import imie.campus.dao.CarpoolingService;
import imie.campus.model.dto.StepTO;
import imie.campus.model.entities.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static imie.campus.core.mappers.converters.Converters.mapper;
import static imie.campus.core.mappers.converters.StandardConverters.*;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Service
public class StepMapper extends AbstractEntityMapper<Step, StepTO> {

    private final CarpoolingService carpoolingService;
    private final LocationMapper locationMapper;

    @Autowired
    public StepMapper(CarpoolingService carpoolingService, LocationMapper locationMapper) {
        super(Step.class, StepTO.class);
        this.carpoolingService = carpoolingService;
        this.locationMapper = locationMapper;
    }

    @Override
    public void configure() {
        defaultTypeMap().addMappings(mapping -> {
            mapping.using(toPrimaryKey()).map(Step::getCarpooling, StepTO::setCarpoolingId);
            mapping.using(mapper(locationMapper)).map(Step::getLocation, StepTO::setLocation);

            mapping.using(dateToString(ISO_DATE_TIME)).map(Step::getEstimatedDeparture, StepTO::setEstimatedDeparture);
            mapping.using(dateToString(ISO_DATE_TIME)).map(Step::getEffectiveDeparture, StepTO::setEffectiveDeparture);
            mapping.using(dateToString(ISO_DATE_TIME)).map(Step::getEstimatedArriving, StepTO::setEstimatedArriving);
            mapping.using(dateToString(ISO_DATE_TIME)).map(Step::getEffectiveArriving, StepTO::setEffectiveArriving);
        });
        reverseTypeMap().addMappings(mapping -> {
            mapping.using(fromPrimaryKey(carpoolingService)).map(StepTO::getCarpoolingId, Step::setCarpooling);
            mapping.using(ReverseConverters.reverseMapper(locationMapper)).map(StepTO::getLocation, Step::setLocation);

            mapping.using(dateFromString(ISO_DATE_TIME)).map(StepTO::getEstimatedDeparture, Step::setEstimatedDeparture);
            mapping.using(dateFromString(ISO_DATE_TIME)).map(StepTO::getEffectiveDeparture, Step::setEffectiveDeparture);
            mapping.using(dateFromString(ISO_DATE_TIME)).map(StepTO::getEstimatedArriving, Step::setEstimatedArriving);
            mapping.using(dateFromString(ISO_DATE_TIME)).map(StepTO::getEffectiveArriving, Step::setEffectiveArriving);
        });
    }
}
