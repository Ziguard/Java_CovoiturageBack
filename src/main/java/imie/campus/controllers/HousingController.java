package imie.campus.controllers;

import imie.campus.dao.HousingService;
import imie.campus.model.dto.HousingTO;
import imie.campus.model.entities.Housing;
import imie.campus.model.mappers.HousingMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static imie.campus.controllers.HousingController.RESOURCE_PATH;

@RestController
@RequestMapping(RESOURCE_PATH)
public class HousingController extends AnnounceController<Housing, HousingTO> {
    static final String RESOURCE_PATH = "/housings";

    public HousingController(HousingService service, HousingMapper mapper) {
        super(service, mapper);
    }

    @Override
    public String getResourcePath() {
        return RESOURCE_PATH;
    }
}
