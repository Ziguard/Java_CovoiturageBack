package imie.campus.dao;

import imie.campus.core.listeners.EntityListener;
import imie.campus.core.repositories.EntityRepository;
import imie.campus.core.services.AbstractEntityService;
import imie.campus.model.entities.Announce;

public abstract class AnnounceService<A extends Announce> extends AbstractEntityService<A, Integer> {
    protected AnnounceService(
            EntityRepository<A, Integer> repository,
            EntityListener<A> listener) {
        super(repository, listener);
    }
}
