package imie.campus.core.contexts;

import javax.servlet.ServletRequest;

public interface ContextProvider<C extends RequestContext> {
    C provide(final ServletRequest request);
}
