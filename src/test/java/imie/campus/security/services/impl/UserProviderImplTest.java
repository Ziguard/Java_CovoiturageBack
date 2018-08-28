package imie.campus.security.services.impl;

import imie.campus.model.entities.User;
import imie.campus.model.repositories.UserRepository;
import imie.campus.security.services.UserProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserProviderImplTest {

    static UserProvider service;

    static UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        service = new UserProviderImpl(userRepository);
    }

    @AfterEach
    void tearDown() {
        service = null;
        userRepository = null;
    }

    @Test
    void testFindByUsername() {
        User user = mock(User.class);
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        assertEquals(user, service.findByUsername("someone"));
    }
}