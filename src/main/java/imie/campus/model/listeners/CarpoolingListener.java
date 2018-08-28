package imie.campus.model.listeners;

import imie.campus.model.entities.Carpooling;
import imie.campus.model.mappers.AnnounceHMapper;
import imie.campus.model.repositories.AnnounceHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarpoolingListener extends AnnounceListener<Carpooling> {
    @Autowired
    public CarpoolingListener(AnnounceHRepository repository,
                              AnnounceHMapper mapper) {
        super(repository, mapper);
    }
}
