package imie.campus.core.services;

import imie.campus.core.contexts.RequestContext;
import imie.campus.core.entities.Assignable;
import imie.campus.core.entities.BaseEntity;
import imie.campus.core.listeners.BaseEntityListener;
import imie.campus.core.listeners.EntityListener;
import imie.campus.core.repositories.EntityRepository;
import imie.campus.errors.exceptions._404.ResourceNotFoundException;
import imie.campus.errors.exceptions._409.ConflictException;
import imie.campus.errors.exceptions._500.EntityPersistException;
import imie.campus.errors.exceptions._500.TechnicalException;
import imie.campus.errors.exceptions._501.NotModifiableException;
import imie.campus.utils.rest.ServiceUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static imie.campus.utils.commons.GeneralUtils.findLastExceptionInHierarchy;
import static imie.campus.utils.rest.ServiceUtils.fromIterable;

/**
 * A base class for data-centric entity services, that
 *   have a repository for the entity.
 * @param <E> The type of the entity managed by the services
 * @param <ID> The type of the entity primary key
 */
public abstract class AbstractEntityService
        <E extends BaseEntity<ID>, ID extends Serializable> implements EntityService<E, ID>
{
    /**
     * The underlying repository instance.
     * An implementation of EntityRepository
     */
    protected final EntityRepository<E, ID> repository;

    /**
     * The associated listener for this entity service.
     * An implementation of an EntityListener for the current entity
     */
    protected final EntityListener<E> listener;

    /**
     * {@inheritDoc}
     */
    protected AbstractEntityService(
            final EntityRepository<E, ID> repository) {
        this.repository = repository;
        this.listener = new BaseEntityListener<>();
    }

    /**
     * {@inheritDoc}
     */
    protected AbstractEntityService(EntityRepository<E, ID> repository,
                                    EntityListener<E> listener) {
        this.repository = repository;
        this.listener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E find(final ID identifier) throws ResourceNotFoundException {
        if (!repository.exists(identifier)) {
            throw new ResourceNotFoundException(identifier);
        }
        return repository.findOne(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E create(E entity, RequestContext context)
            throws TechnicalException, ConflictException {
        if (entity.primaryKey() != null)
            throw new PersistenceException("Unable to create an already existing entity into the database.");

        Specifications<E> specs = ServiceUtils.buildUniqueSearchCriteria(entity);
        E alreadyExisting = specs != null ? findOne(specs) : null;

        if (alreadyExisting != null)
            throw new ConflictException(entity.getClass(), alreadyExisting.primaryKey());

        try {
            listener.beforeCreate(entity, context);
            repository.save(entity);
            listener.afterCreate(entity, context);
            return entity;
        } catch (DataAccessException ex) {
            throw new EntityPersistException(findLastExceptionInHierarchy(ex));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public E update(ID id, E entity, RequestContext context) {
        E actual = find(id);

        if (!(entity instanceof Assignable))
            throw new NotModifiableException(entity.getClass());

        try {
            listener.beforeUpdate(actual, entity, context);
            ((Assignable<E>) actual).assign(entity, context);
            entity = actual;
            repository.save(entity);
            listener.afterUpdate(entity, context);
            return entity;
        } catch (DataAccessException ex) {
            throw new EntityPersistException(findLastExceptionInHierarchy(ex));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> findAll() {
        return fromIterable(repository.findAll(), ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> findAll(int page, int size) {
        final Pageable pageable = new PageRequest(page, size);
        return fromIterable(repository.findAll(pageable), ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> findAll(Sort sort) {
        return fromIterable(repository.findAll(sort), ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> findAll(Iterable<ID> identifiers) {
        return fromIterable(repository.findAll(identifiers), ArrayList::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public <Pr> List<E> findAllBy(String propertyName, Pr candidateValue)
            throws ResourceNotFoundException {
        return findAll((root, query, cb) -> cb.equal(root.get(propertyName), candidateValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(E entity, RequestContext context)
            throws TechnicalException, ResourceNotFoundException {
        listener.beforeRemove(entity, context);
        repository.delete(entity);
        listener.afterRemove(entity, context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E findOne(Specification<E> spec) {
        return repository.findOne(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> findAll(Specification<E> spec) {
        return repository.findAll(spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<E> findAll(Specification<E> spec, Sort sort) {
        return repository.findAll(spec, sort);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(Specification<E> spec) {
        return repository.count(spec);
    }
}
