package imie.campus.model.listeners;

import imie.campus.core.contexts.AuthRequestContext;
import imie.campus.core.contexts.RequestContext;
import imie.campus.core.listeners.BaseEntityListener;
import imie.campus.model.entities.Announce;
import imie.campus.model.entities.AnnounceH;
import imie.campus.model.entities.User;
import imie.campus.model.enums.AnnounceStateEnum;
import imie.campus.model.mappers.AnnounceHMapper;
import imie.campus.model.repositories.AnnounceHRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AnnounceListener<A extends Announce> extends BaseEntityListener<A> {

    private final AnnounceHRepository repository;
    private final AnnounceHMapper mapper;

    @Autowired
    protected AnnounceListener(AnnounceHRepository repository,
                               AnnounceHMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void beforeUpdate(A actual, A updated, RequestContext context) {
        AnnounceH history = mapper.map(actual);
        if (context instanceof AuthRequestContext) {
            Optional<User> user = ((AuthRequestContext<User>) context).authenticatedUser();
            user.ifPresent(history::setModifier);
        }
        repository.save(history);
    }

    @Override
    public void beforeCreate(A newer, RequestContext context) {
        if (context instanceof AuthRequestContext) {
            Optional<User> user = ((AuthRequestContext<User>) context).authenticatedUser();
            user.ifPresent(newer::setOwner);
        }
        newer.setCreationDate(LocalDateTime.now());
        newer.setStatus(AnnounceStateEnum.SUBMITTED);
    }
}
