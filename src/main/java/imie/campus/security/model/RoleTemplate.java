package imie.campus.security.model;

import java.util.Optional;

/**
 * Defines the general behaviour for a role supporting by the security layer.
 * A role must have a key, which means a string unique identifier, and can
 * have a parent role from which its inherits.
 *
 * @author Fabien
 * @param <A> The type of possible aditionnal informations for the role
 */
public interface RoleTemplate<A> {
    /**
     * Returns the role key
     * @return The role key
     */
    String getKey();

    /**
     * Returns the possible parent role
     * @return The parent role object, or null if there is no one
     */
    RoleTemplate<A> getParent();

    /**
     * Gives additionnal informations about the role
     * @return The <A> typed instance containing the role
     */
    default Optional<A> getAdditionnalInfo() {
        return Optional.empty();
    }
}
