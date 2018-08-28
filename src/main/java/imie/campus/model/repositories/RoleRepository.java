package imie.campus.model.repositories;

import imie.campus.core.repositories.EntityRepository;
import imie.campus.model.entities.Role;

public interface RoleRepository extends EntityRepository<Role, Integer> {
    Role findByKey(final String key);
}
