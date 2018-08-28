package imie.campus.model;

import imie.campus.model.entities.User;

public interface AuthenticationContext {
    User authenticatedUser();

    static AuthenticationContext emptyContext() { return () -> null; }
    static AuthenticationContext from(User user) { return () -> user; }

    default boolean isAuthenticated() { return authenticatedUser() != null; }
}
