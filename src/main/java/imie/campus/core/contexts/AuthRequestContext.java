package imie.campus.core.contexts;

import imie.campus.security.model.UserTemplate;

import javax.servlet.ServletRequest;
import java.util.Optional;

public interface AuthRequestContext<U extends UserTemplate<?>> extends RequestContext {
    Optional<U> authenticatedUser();

    static <U extends UserTemplate<?>> AuthRequestContext from(
            final ServletRequest request,
            final U user) {
        return new AuthRequestContext() {
            @Override
            public Optional<? extends UserTemplate<?>> authenticatedUser() {
                return Optional.ofNullable(user);
            }

            @Override
            public ServletRequest request() {
                return request;
            }
        };
    }
}
