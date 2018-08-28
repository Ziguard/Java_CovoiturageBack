package imie.campus.core.services;

import imie.campus.core.contexts.RequestContext;
import imie.campus.core.entities.BaseEntity;
import imie.campus.errors.exceptions._404.ResourceNotFoundException;
import imie.campus.errors.exceptions._409.ConflictException;
import imie.campus.errors.exceptions._500.TechnicalException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a services that behaves as a manager for a specific entity.
 * The default implementation of this interface is to avoid code replication between
 *   DAO services method and the underlying associated repository.
 * EntityService provides delegates methods for all impl CRUD operation,
 * a support of the JPA Criteria API (Specifications API compliant) and a
 * functionnal validation mechanism based on rules that allows to delegate the validation
 * process to a dedicated class.
 * @param <E> The type of managed entity
 * @param <ID> The type of the entity's primary key.
 * @author Fabien
 */
public interface EntityService<E extends BaseEntity<ID>, ID extends Serializable> {

    /**
     * Find a copy of the entity by its identifier from the database.
     * @param identifier The object identifier
     * @return An instance of the entity
     * @throws ResourceNotFoundException In case of the resource has not been found
     */
    E find(final ID identifier) throws ResourceNotFoundException;

    /**
     * Persist an entity object in the database.
     * @param entity The entity object to save
     * @param context The authentication context from the request
     * @throws TechnicalException The entity could not be saved in the database
     * @throws ConflictException A similar copy of the entity already exists
     */
    E create(E entity, RequestContext context)
            throws TechnicalException, ConflictException;

    /**
     * Update the provided entity in the database.
     * @param id The entity id
     * @param entity The entity to update
     * @param context The authentication context from the request
     * @throws PersistenceException Thrown if the given entity does not exist in the database
     */
    E update(ID id, E entity, RequestContext context);

    /**
     * Gets all the entities from the database.
     * @return A list of all the entities
     */
    List<E> findAll();

    /**
     * Retrieves all the entities from database, with pagination.
     * @param page The current page index
     * @param size The number of elements to put on the page
     * @return A list of all the entities of this page
     */
    List<E> findAll(int page, int size);

    /**
     * Retrieves all the entites from database, sorted according to the
     *   sort object given in argument.
     * @param sort A sort object describing what and how to sort
     * @return A list of all the entites, sorted
     */
    List<E> findAll(Sort sort);

    /**
     * Retrieves all the entities with specified identifiers from the database.
     * @param identifiers A sequence of ID of entity objects to retrieve.
     * @return A list of all the entities
     */
    List<E> findAll(Iterable<ID> identifiers);

    /**
     * Returns the number of all entity objects.
     * @return The number of entities
     */
    long count();

    /**
     * Delete the given entity object.
     * @param entity The entity object to delete
     * @param context The authentication context from the request
     * @throws TechnicalException A persistence error occured during the operation
     * @throws ResourceNotFoundException The entity was not found
     */
    void delete(E entity, RequestContext context)
            throws TechnicalException, ResourceNotFoundException;

    /**
     * Try to find an entity that conforms to the given Specification object.
     * @param spec The Specification object, representing the criteria
     * @return The found object, or null else
     */
    E findOne(Specification<E> spec);

    /**
     * Retrieves all the entities that conforms to the given Specification object.
     * @return A list of the found entities
     */
    List<E> findAll(Specification<E> spec);

    /**
     * Retrieves all the entities that conforms to the given Specification object,
     *   sorted according to the Sort object.
     * @return A list of the found entities, sorted
     */
    List<E> findAll(Specification<E> spec, Sort sort);

    default <Pr> E findAnyBy(String propertyName, Pr candidateValue)
            throws ResourceNotFoundException {
        return findAllBy(propertyName, candidateValue).stream()
                .findAny()
                .orElseThrow(ResourceNotFoundException::new);
    }

    default <Pr> E findFirstBy(String propertyName, Pr candidateValue)
            throws ResourceNotFoundException {
        return findAllBy(propertyName, candidateValue).stream()
                .findFirst()
                .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Finds all the entity for which the property described by the given accessor
     * have the candidate value.
     * @param propertyName A method reference to the entity property
     * @param candidateValue The candidate value
     * @param <Pr> The type of the property
     * @return A list of all the found entities
     */
    <Pr> List<E> findAllBy(String propertyName, Pr candidateValue);

    /**
     * Counts the entities that conforms to the given Specification object.
     * @return The number of found entities
     */
    long count(Specification<E> spec);


    /**
     * Delete the given entity object.
     * @param entity The entity object to delete
     * @throws TechnicalException A persistence error occured during the operation
     * @throws ResourceNotFoundException The entity was not found
     */
    default void delete(E entity)
            throws TechnicalException, ResourceNotFoundException {
        delete(entity, null);
    }

    /**
     * Delete the given entity object.
     * @param id The id of the entity to delete
     * @throws TechnicalException A persistence error occured during the operation
     * @throws ResourceNotFoundException The entity was not found
     */
    default void delete(ID id)
            throws TechnicalException, ConflictException{
        delete(find(id));
    }

    /**
     * Persist an entity object in the database.
     * @param entity The entity object to save
     * @throws TechnicalException The entity could not be saved in the database
     * @throws ConflictException A similar copy of the entity already exists
     */
    default E create(E entity)
            throws TechnicalException, ConflictException {
        return create(entity, null);
    }

    /**
     * Persist a sequence of entity objects in the database.
     * @param entities The sequence of the entities
     * @throws TechnicalException The entity could not be saved in the database
     * @throws ConflictException A similar copy of the entity already exists
     */
    default void create(Iterable<E> entities)
            throws TechnicalException, ConflictException {
        entities.forEach(this::create);
    }

    /**
     * Persist a sequence of entity objects in the database.
     * @param entities The sequence of the entities
     * @param context The authentication context from the request
     * @throws TechnicalException The entity could not be saved in the database
     * @throws ConflictException A similar copy of the entity already exists
     */
    default void create(Iterable<E> entities, RequestContext context)
            throws TechnicalException, ConflictException {
        entities.forEach(entity -> create(entity, context));
    }

    /**
     * Update the provided entity in the database.
     * @param id The entity id
     * @param entity The entity to update
     * @throws PersistenceException Thrown if the given entity does not exist in the database
     */
    default E update(ID id, E entity) {
        return update(id, entity, null);
    }

}
