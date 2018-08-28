package imie.campus.controllers;

import imie.campus.core.controllers.AbstractController;
import imie.campus.core.mappers.AbstractEntityMapper;
import imie.campus.core.services.EntityService;
import imie.campus.model.dto.AnnounceTO;
import imie.campus.model.entities.Announce;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static imie.campus.model.enums.AnnounceStateEnum.REMOVED;
import static javax.security.auth.callback.ConfirmationCallback.OK;

public abstract class AnnounceController<A extends Announce, ATO extends AnnounceTO>
    extends AbstractController<A, ATO, Integer> {

    protected AnnounceController(EntityService<A, Integer> service,
                                 AbstractEntityMapper<A, ATO> mapper) {
        super(service, mapper);
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity removeAnnounce(@PathVariable("id") Integer id) {
        service.find(id).setStatus(REMOVED);
        return ResponseEntity.status(OK).build();
    }
}
