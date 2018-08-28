package imie.campus.dao;

import imie.campus.model.entities.Housing;
import imie.campus.model.listeners.HousingListener;
import imie.campus.model.repositories.HousingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HousingService extends AnnounceService<Housing> {

    @Autowired
    public HousingService(HousingRepository repository,
                          HousingListener listener) {
        super(repository, listener);
    }
}
