package imie.campus.security.services;

import imie.campus.security.model.RoleTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * Defines the behaviour of a services that is responsible for computing
 * hierarchies of roles and to check matching between user roles and authorized ones.
 * @author Fabien
 */
public interface RoleCheckingService {
    /**
     * Computes the hierarchy of a RoleTemplate instance
     * @param baseRole The base role from which build the hierarchy
     * @return The hierarchy roles list
     */
    <A> Collection<RoleTemplate<A>> computeHierarchy(RoleTemplate<A> baseRole);

    /**
     * Indicates whether a role to test matches with the user roles list.
     * @param userRoles A list of roles attributed to the user
     * @param authorizedRole The key of the role to test
     * @return true if the given role belongs to the hierarchy of the role to test, false otherwise
     */
    boolean matches(Set<? extends RoleTemplate<?>> userRoles,
                    String authorizedRole);
}
