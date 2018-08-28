package imie.campus.model.mappers;

import imie.campus.model.entities.Announce;
import imie.campus.model.entities.AnnounceH;
import imie.campus.model.repositories.AnnounceHRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AnnounceHMapper extends ModelMapper {

    private final AnnounceHRepository repository;

    @Autowired
    public AnnounceHMapper(AnnounceHRepository repository) {
        this.repository = repository;
    }

    public AnnounceH map(Announce announce) {
        AnnounceH history = this.map(announce, AnnounceH.class);
        history.setId(null);
        history.setOriginal(announce);
        history.setRevision(repository.incMaxRevision());

        return history;
    }
}
