package imie.campus.controllers;

import imie.campus.dao.ServiceService;
import imie.campus.model.dto.ServiceTO;
import imie.campus.model.entities.Service;
import imie.campus.model.mappers.ServiceMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static imie.campus.controllers.ServiceController.RESOURCE_PATH;

@RestController
@RequestMapping(RESOURCE_PATH)
public class ServiceController extends AnnounceController<Service, ServiceTO> {
    static final String RESOURCE_PATH = "/services";

    public ServiceController(ServiceService service, ServiceMapper mapper) {
        super(service, mapper);
    }

    @Override
    public String getResourcePath() {
        return RESOURCE_PATH;
    }
}
