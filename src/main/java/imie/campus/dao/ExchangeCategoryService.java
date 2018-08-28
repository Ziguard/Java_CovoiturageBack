package imie.campus.dao;

import imie.campus.core.services.AbstractEntityService;
import imie.campus.model.entities.ExchangeCategory;
import imie.campus.model.repositories.ExchangeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeCategoryService extends AbstractEntityService<ExchangeCategory, Integer> {
    @Autowired
    protected ExchangeCategoryService(ExchangeCategoryRepository repository) {
        super(repository);
    }
}
