package imie.campus.dao;

import imie.campus.model.entities.Exchange;
import imie.campus.model.listeners.ExchangeListener;
import imie.campus.model.repositories.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService extends AnnounceService<Exchange> {

    @Autowired
    public ExchangeService(ExchangeRepository repository,
                           ExchangeListener listener) {
        super(repository, listener);
    }
}
