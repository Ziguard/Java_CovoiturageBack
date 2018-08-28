package imie.campus.core.listeners;

import imie.campus.core.contexts.RequestContext;
import imie.campus.core.entities.BaseEntity;

import java.io.Serializable;

/**
 * Describe an entity listener, that allowed to execute code and modify entity before or after standard
 * persistance operations, such as update, create or remove.
 * @param <E> The type of the entity
 */
public interface EntityListener<E extends BaseEntity<? extends Serializable>> {
    /**
     * Method executed before each entity updates.
     * @param actual The actual version of the entity
     * @param updated The entity with modifications to persist
     * @param context The context created from the request
     */
    void beforeUpdate(E actual, E updated, RequestContext context);

    /**
     * Method executed after each entity updates.
     * @param updated The entity with modifications to persist
     * @param context The context created from the request
     */
    void afterUpdate(E updated, RequestContext context);

    /**
     * Method executed before each entity creation.
     * @param newer The entity to create
     * @param context The context from the request
     */
    void beforeCreate(E newer, RequestContext context);

    /**
     * Method executed after each entity creation.
     * @param created The entity after its creation in the database
     * @param context The context created from the request
     */
    void afterCreate(E created, RequestContext context);

    /**
     * Method executed before each entity removal.
     * @param actual The entity before it will be removed
     * @param context The context created from the request
     */
    void beforeRemove(E actual, RequestContext context);

    /**
     * Method executed after each entity removal.
     * @param removed A copy of the removed entity
     * @param context The context created from the request
     */
    void afterRemove(E removed, RequestContext context);
}
