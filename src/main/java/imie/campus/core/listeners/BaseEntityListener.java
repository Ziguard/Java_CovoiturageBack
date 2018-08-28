package imie.campus.core.listeners;

import imie.campus.core.contexts.RequestContext;
import imie.campus.core.entities.BaseEntity;

import java.io.Serializable;

public class BaseEntityListener<E extends BaseEntity<? extends Serializable>> implements EntityListener<E> {
    @Override
    public void beforeUpdate(E actual, E updated, RequestContext context) {}

    @Override
    public void afterUpdate(E updated, RequestContext context) {}

    @Override
    public void beforeCreate(E newer, RequestContext context) {}

    @Override
    public void afterCreate(E created, RequestContext context) {}

    @Override
    public void beforeRemove(E actual, RequestContext context) {}

    @Override
    public void afterRemove(E removed, RequestContext context) {}
}
