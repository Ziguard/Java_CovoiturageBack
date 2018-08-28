package imie.campus.model.mappers;

import imie.campus.dao.ExchangeCategoryService;
import imie.campus.dao.PictureService;
import imie.campus.model.dto.ExchangeTO;
import imie.campus.model.dto.PictureTO;
import imie.campus.model.entities.Exchange;
import imie.campus.model.enums.ExchangeTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static imie.campus.core.mappers.converters.Converters.collectionMapper;
import static imie.campus.core.mappers.converters.ReverseConverters.reverseCollectionFunction;
import static imie.campus.core.mappers.converters.StandardConverters.*;

@Service
@Scope("singleton")
public class ExchangeMapper extends AnnounceMapper<Exchange, ExchangeTO> {
    private final ExchangeCategoryService exchangeCategoryService;
    private final PictureMapper pictureMapper;
    private final PictureService pictureService;

    @Autowired
    public ExchangeMapper(UserMapper userMapper,
                          PictureMapper pictureMapper,
                          PictureService pictureService,
                          ExchangeCategoryService exchangeCategoryService) {
        super(Exchange.class, ExchangeTO.class, userMapper);
        this.exchangeCategoryService = exchangeCategoryService;
        this.pictureMapper = pictureMapper;
        this.pictureService = pictureService;
    }

    @Override
    public void configure() {
        super.configure();
        defaultTypeMap().addMappings(mapper -> {
            mapper.using(toPrimaryKey()).map(Exchange::getCategory, ExchangeTO::setCategory);
            mapper.using(enumToString()).map(Exchange::getExchangeType, ExchangeTO::setExchangeType);
            mapper.using(collectionMapper(pictureMapper, HashSet::new))
                    .map(Exchange::getPictures, ExchangeTO::setPictures);
        });
        reverseTypeMap().addMappings(mapper -> {
            mapper.using(fromPrimaryKey(exchangeCategoryService))
                    .map(ExchangeTO::getCategory, Exchange::setCategory);
            mapper.using(enumFromString(ExchangeTypeEnum.class))
                    .map(ExchangeTO::getExchangeType, Exchange::setExchangeType);

            /* Retrieve picture only by id from the TO to avoid
             detached entity issues */
            mapper.using(reverseCollectionFunction(
                    (PictureTO pto) -> pictureService.find(pto.getId()), HashSet::new))
                    .map(ExchangeTO::getPictures, Exchange::setPictures);
        });
    }
}


