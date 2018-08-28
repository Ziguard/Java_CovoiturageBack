package imie.campus.security.model;

import java.util.Optional;
import java.util.Set;

/**
 * Defines the behaviour of a User object in the security layer.
 * This interface is used to reduce the coupling between persistence objects
 * and security concerns.
 * @param <P> The type of the user password
 * @author Fabien
 */
public interface UserTemplate<P> {
    /**
     * Returns the username.
     * @return The username
     */
    String getUsername();

    /**
     * Returns the password.
     * @return The password
     */
    P getPassword();

    /**
     * Returns a possible salt stored with the user.
     * @return A optional of the salt, if exists
     */
    Optional<String> getSalt();

    /**
     * A set of the user attributed roles.
     * @return The user roles
     * @see RoleTemplate
     */
    Set<? extends RoleTemplate<?>> getRoles();

    /**
     * Indicates if a user is active or not.
     * A not active user cannot log in to the WebService.
     * @return A boolean indicating whether user is active or not
     */
    boolean isActive();
}
