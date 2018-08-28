package imie.campus.security.services.impl;

import imie.campus.model.entities.Role;
import imie.campus.security.model.RoleTemplate;
import imie.campus.security.services.RoleProvider;
import imie.campus.dao.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RoleProviderImplTest {

    static RoleProvider provider;

    static RoleService roleService;

    @BeforeEach
    void setUp() {
        roleService = mock(RoleService.class);
        provider = new RoleProviderImpl(roleService);
    }

    @AfterEach
    void tearDown() {
        provider = null;
        roleService = null;
    }

    @Test
    void testGetRoles() {
        List<Role> roles = asList(
            role("ADMIN"), role("MODERATOR"), role("USER"));
        when(roleService.findAll()).thenReturn(roles);
        provider.update(); // Update to make roleService.findAll stubbing effective in the provider

        List<String> keys = provider.getRoles().stream()
                .map(RoleTemplate::getKey)
                .collect(Collectors.toList());

        assertThat(keys, contains("ADMIN", "MODERATOR", "USER"));
    }

    static Role role(final String key) {
        Role role = new Role();
        role.setKey(key);
        return role;
    }
}