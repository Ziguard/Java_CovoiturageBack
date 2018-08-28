package imie.campus.dao;

import imie.campus.model.entities.Carpooling;
import imie.campus.model.listeners.CarpoolingListener;
import imie.campus.model.repositories.CarpoolingRepository;
import org.springframework.stereotype.Service;

@Service
public class CarpoolingService extends AnnounceService<Carpooling> {
    public CarpoolingService(CarpoolingRepository repository,
                             CarpoolingListener listener) {
        super(repository, listener);
    }
}
