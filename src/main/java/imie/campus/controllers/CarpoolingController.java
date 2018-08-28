package imie.campus.controllers;

import imie.campus.dao.CarpoolingService;
import imie.campus.model.dto.CarpoolingTO;
import imie.campus.model.entities.Carpooling;
import imie.campus.model.mappers.CarpoolingMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static imie.campus.controllers.CarpoolingController.RESOURCE_PATH;

@RestController
@RequestMapping(RESOURCE_PATH)
public class CarpoolingController extends AnnounceController<Carpooling, CarpoolingTO> {
    static final String RESOURCE_PATH = "/carpoolings";

    public CarpoolingController(CarpoolingService service, CarpoolingMapper mapper) {
        super(service, mapper);
    }

    @Override
    public String getResourcePath() {
        return RESOURCE_PATH;
    }
}
