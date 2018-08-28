package imie.campus.dao;

import imie.campus.core.services.AbstractEntityService;
import imie.campus.model.entities.User;
import imie.campus.model.listeners.UserListener;
import imie.campus.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractEntityService<User, Integer> {
    @Autowired
    public UserService(UserRepository repository, UserListener listener) {
        super(repository, listener);
    }

    public User findByUsername(final String username) {
        return findAnyBy("username", username);
    }
}
