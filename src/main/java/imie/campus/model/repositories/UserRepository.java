package imie.campus.model.repositories;

import imie.campus.core.repositories.EntityRepository;
import imie.campus.model.entities.User;

public interface UserRepository extends EntityRepository<User, Integer> {
    User findByUsername(final String username);
}
