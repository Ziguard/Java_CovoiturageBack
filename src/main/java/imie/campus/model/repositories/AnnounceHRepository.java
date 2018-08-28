package imie.campus.model.repositories;

import imie.campus.core.repositories.EntityRepository;
import imie.campus.model.entities.AnnounceH;
import org.springframework.data.jpa.repository.Query;

public interface AnnounceHRepository extends EntityRepository<AnnounceH,Integer> {

    @Query("select max(h.revision) from AnnounceH h")
    Integer maxRevision();

    default Integer incMaxRevision() {
        return (maxRevision() == null) ? 1 : maxRevision() + 1;
    }
}
