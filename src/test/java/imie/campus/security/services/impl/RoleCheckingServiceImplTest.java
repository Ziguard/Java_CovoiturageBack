package imie.campus.security.services.impl;

import imie.campus.security.model.RoleTemplate;
import imie.campus.security.services.RoleCheckingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static imie.campus.security.mocks.MockedRoles.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoleCheckingServiceImplTest {

    static RoleCheckingService roleCheckingService;

    @BeforeEach
    void setup() {
        roleCheckingService = new RoleCheckingServiceImpl();
    }

    /**
     * Test de computeHierarchy()
     * @see RoleCheckingService#computeHierarchy(RoleTemplate)
     */
    @Test
    @SuppressWarnings("unchecked")
    void testComputeHierarchy() {
        assertThat(roleCheckingService.computeHierarchy(admin()),
                contains( admin(), moderator(), user() ));

        assertThat(roleCheckingService.computeHierarchy(moderator()),
                contains( moderator(), user() ));

        assertThat(roleCheckingService.computeHierarchy(user()),
                contains( user() ));

        assertThat(roleCheckingService.computeHierarchy(executor()),
                contains( executor(), writer(), reader() ));

        assertThat(roleCheckingService.computeHierarchy(writer()),
                contains( writer(), reader() ));

        assertThat(roleCheckingService.computeHierarchy(reader()),
                contains( reader() ));
    }

    /**
     * Lorsque les rôles correspondent (cas passants)
     * @see RoleCheckingService#matches(Set, String)
     */
    @Test
    void testMatchesOK() {
        assertTrue(roleCheckingService.matches(
                set(reader()), "READER"));

        assertTrue(roleCheckingService.matches(
                set(writer(), reader()), "READER"));

        assertTrue(roleCheckingService.matches(
                set(writer(), reader()), "WRITER"));
    }

    /**
     * Lorsque les rôles ne correspondent pas (cas bloquants)
     * @see RoleCheckingService#matches(Set, String)
     */
    @Test
    void testMatchesKO() {
        assertFalse(roleCheckingService.matches(
                set(writer(), admin()), "EXECUTOR"));

        assertFalse(roleCheckingService.matches(
                set(writer(), moderator()), "ADMIN"));

        assertFalse(roleCheckingService.matches(
                set(user()), "MODERATOR"));

        assertFalse(roleCheckingService.matches(
                set(reader()), "WRITER"));
    }

    /**
     * Méthodes utilitaires
     */
    @SafeVarargs
    public static <T> Set<T> set(T ... items) {
        return new HashSet<>(asList(items));
    }
}