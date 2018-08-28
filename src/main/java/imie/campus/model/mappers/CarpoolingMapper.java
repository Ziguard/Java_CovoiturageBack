package imie.campus.model.mappers;

import imie.campus.dao.UserService;
import imie.campus.model.dto.CarpoolingTO;
import imie.campus.model.entities.Carpooling;
import imie.campus.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static imie.campus.core.mappers.converters.Converters.*;
import static imie.campus.core.mappers.converters.ReverseConverters.*;
import static imie.campus.core.mappers.converters.StandardConverters.dateFromString;
import static imie.campus.core.mappers.converters.StandardConverters.dateToString;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Service
public class CarpoolingMapper extends AnnounceMapper<Carpooling, CarpoolingTO> {

    private final UserService userService;
    private final LagMapper lagMapper;
    private final StepMapper stepMapper;

    @Autowired
    public CarpoolingMapper(UserMapper userMapper, UserService userService,
                            LagMapper lagMapper, StepMapper stepMapper) {
        super(Carpooling.class, CarpoolingTO.class, userMapper);
        this.userService = userService;
        this.lagMapper = lagMapper;
        this.stepMapper = stepMapper;
    }

    @Override
    public void configure() {
        super.configure();
        defaultTypeMap().addMappings(mapping -> {
            mapping.using(function(User::getUsername)).map(Carpooling::getDriver, CarpoolingTO::setDriver);
            mapping.using(dateToString(ISO_DATE_TIME)).map(Carpooling::getCreatedDate, CarpoolingTO::setCreatedDate);
            mapping.using(collectionMapper(lagMapper, HashSet::new)).map(Carpooling::getLags, CarpoolingTO::setLags);
            mapping.using(collectionMapper(stepMapper, HashSet::new)).map(Carpooling::getSteps, CarpoolingTO::setSteps);
            mapping.using(collectionFunction(User::getUsername, HashSet::new)).map(Carpooling::getPassengers, CarpoolingTO::setPassengers);
        });
        reverseTypeMap().addMappings(mapping -> {
            mapping.using(reverseFunction(userService::findByUsername)).map(CarpoolingTO::getDriver, Carpooling::setDriver);
            mapping.using(dateFromString(ISO_DATE_TIME)).map(CarpoolingTO::getCreatedDate, Carpooling::setCreatedDate);
            mapping.using(reverseCollectionMapper(lagMapper, HashSet::new)).map(CarpoolingTO::getLags, Carpooling::setLags);
            mapping.using(reverseCollectionMapper(stepMapper, HashSet::new)).map(CarpoolingTO::getSteps, Carpooling::setSteps);
            mapping.using(reverseCollectionFunction(userService::findByUsername, HashSet::new))
                    .map(CarpoolingTO::getPassengers, Carpooling::setPassengers);
        });
    }

    @Override
    public Carpooling reverse(CarpoolingTO target) {
        Carpooling source = super.reverse(target);
        source.getLags().forEach(lag -> lag.setCarpooling(source));
        source.getSteps().forEach(step -> step.setCarpooling(source));
        return source;
    }
}
