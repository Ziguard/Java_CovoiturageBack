package imie.campus.dao;

import imie.campus.model.entities.Service;
import imie.campus.model.listeners.ServiceListener;
import imie.campus.model.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceService extends AnnounceService<Service>{

    @Autowired
    public ServiceService(ServiceRepository repository, ServiceListener listener) {
        super(repository, listener);
    }
}
