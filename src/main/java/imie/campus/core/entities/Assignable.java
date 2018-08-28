package imie.campus.core.entities;

import imie.campus.core.contexts.RequestContext;

public interface Assignable<A> {
    void assign(A other, RequestContext context);
}
