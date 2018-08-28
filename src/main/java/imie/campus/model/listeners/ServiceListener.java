package imie.campus.model.listeners;

import imie.campus.model.entities.Service;
import imie.campus.model.mappers.AnnounceHMapper;
import imie.campus.model.repositories.AnnounceHRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceListener extends AnnounceListener<Service> {

    @Autowired
    protected ServiceListener(AnnounceHRepository repository,
                              AnnounceHMapper mapper) {
        super(repository, mapper);
    }
}
