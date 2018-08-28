package imie.campus.security.services;

import imie.campus.security.model.RoleTemplate;

import java.util.List;

/**
 * Defines the behaviour of a role provider, used by the security layer to
 *   retrieve a list of the role in the WebService.
 * @author Fabien
 */
public interface RoleProvider {
    /**
     * Renvoi la liste de tous les rôles à prendre en compte
     * @return La liste des rôles
     */
    List<RoleTemplate<?>> getRoles();

    void update();
}
