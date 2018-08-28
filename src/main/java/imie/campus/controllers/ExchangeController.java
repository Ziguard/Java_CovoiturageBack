package imie.campus.controllers;

import imie.campus.dao.ExchangeService;
import imie.campus.model.dto.ExchangeTO;
import imie.campus.model.entities.Exchange;
import imie.campus.model.mappers.ExchangeMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static imie.campus.controllers.ExchangeController.RESOURCE_PATH;

    @RestController
    @RequestMapping(RESOURCE_PATH)
    public class ExchangeController extends AnnounceController<Exchange, ExchangeTO> {
        static final String RESOURCE_PATH = "/exchanges";

        public ExchangeController(ExchangeService service, ExchangeMapper mapper) {
            super(service, mapper);
        }

        @Override
        public String getResourcePath() {
            return RESOURCE_PATH;
        }
    }




