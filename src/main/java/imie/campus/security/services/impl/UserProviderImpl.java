package imie.campus.security.services.impl;

import imie.campus.model.repositories.UserRepository;
import imie.campus.security.model.UserTemplate;
import imie.campus.security.services.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProviderImpl implements UserProvider {

    private final UserRepository userRepository;

    @Autowired
    public UserProviderImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserTemplate<?> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
