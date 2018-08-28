package imie.campus.security.services;

import imie.campus.security.model.UserTemplate;

/**
 * The behaviour of a services that can provide User objects from a username.
 * @author Fabien
 */
public interface UserProvider {
    /**
     * Find a User object by its username.
     * @param username The username to search
     * @return The User object
     */
    UserTemplate<?> findByUsername(String username);
}
