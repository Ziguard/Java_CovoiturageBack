package imie.campus.dao;

import imie.campus.core.services.AbstractEntityService;
import imie.campus.model.entities.ServiceCategory;
import imie.campus.model.repositories.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceCategoryService extends AbstractEntityService<ServiceCategory, Integer> {
    @Autowired
    protected ServiceCategoryService(ServiceCategoryRepository repository) {
        super(repository);
    }
}
