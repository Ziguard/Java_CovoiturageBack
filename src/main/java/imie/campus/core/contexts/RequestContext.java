package imie.campus.core.contexts;

import javax.servlet.ServletRequest;

public interface RequestContext {
    ServletRequest request();

    static RequestContext from(final ServletRequest request) {
        return () -> request;
    }
}
