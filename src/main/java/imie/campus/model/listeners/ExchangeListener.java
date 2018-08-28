package imie.campus.model.listeners;

import imie.campus.model.entities.Exchange;
import imie.campus.model.mappers.AnnounceHMapper;
import imie.campus.model.repositories.AnnounceHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeListener extends AnnounceListener<Exchange> {

    @Autowired
    protected ExchangeListener(AnnounceHRepository repository,
                              AnnounceHMapper mapper) {
        super(repository, mapper);
    }
}