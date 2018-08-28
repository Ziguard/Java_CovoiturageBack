package imie.campus.model.mappers;

import imie.campus.model.dto.HousingTO;
import imie.campus.model.entities.Housing;
import imie.campus.model.enums.HousingTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static imie.campus.core.mappers.converters.StandardConverters.enumFromString;
import static imie.campus.core.mappers.converters.StandardConverters.enumToString;

@Service
public class HousingMapper extends AnnounceMapper<Housing, HousingTO> {

    @Autowired
    public HousingMapper(UserMapper userMapper) {
        super(Housing.class, HousingTO.class, userMapper);
    }

    @Override
    public void configure() {
        super.configure();
        defaultTypeMap().addMappings(mapping ->
                mapping.using(enumToString()).map(Housing::getHousingType, HousingTO::setHousingType));
        reverseTypeMap().addMappings(mapping ->
                mapping.using(enumFromString(HousingTypeEnum.class)).map(HousingTO::getHousingType, Housing::setHousingType));
    }
}
