package imie.campus.model.mappers;

import imie.campus.dao.ServiceCategoryService;
import imie.campus.model.dto.ServiceTO;
import imie.campus.model.entities.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import static imie.campus.core.mappers.converters.StandardConverters.fromPrimaryKey;
import static imie.campus.core.mappers.converters.StandardConverters.toPrimaryKey;

@org.springframework.stereotype.Service
@Scope("singleton")
public class ServiceMapper extends AnnounceMapper<Service, ServiceTO> {
    private final ServiceCategoryService serviceCategoryService;

    @Autowired
    public ServiceMapper(UserMapper userMapper,
                          ServiceCategoryService serviceCategoryService) {
        super(Service.class, ServiceTO.class, userMapper);
        this.serviceCategoryService = serviceCategoryService;
    }

    @Override
    public void configure() {
        super.configure();
        defaultTypeMap().addMappings(mapper ->
                mapper.using(toPrimaryKey()).map(Service::getCategory, ServiceTO::setCategory));
        reverseTypeMap().addMappings(mapper ->
                mapper.using(fromPrimaryKey(serviceCategoryService)).map(ServiceTO::getCategory, Service::setCategory));
    }
}

