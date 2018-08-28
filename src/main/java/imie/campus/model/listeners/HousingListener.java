package imie.campus.model.listeners;

import imie.campus.model.entities.Housing;
import imie.campus.model.mappers.AnnounceHMapper;
import imie.campus.model.repositories.AnnounceHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HousingListener extends AnnounceListener<Housing> {

    @Autowired
    protected HousingListener(AnnounceHRepository repository,
                              AnnounceHMapper mapper) {
        super(repository, mapper);
    }
}
