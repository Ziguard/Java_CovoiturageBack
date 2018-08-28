package imie.campus.security.services.impl;

import imie.campus.errors.exceptions._401.AuthenticationException;
import imie.campus.errors.exceptions._401.AuthenticationFailedException;
import imie.campus.security.encrypters.Encrypter;
import imie.campus.security.model.Credentials;
import imie.campus.security.model.LoginResponse;
import imie.campus.security.model.RoleTemplate;
import imie.campus.security.model.UserTemplate;
import imie.campus.security.model.impl.LoginResponseImpl;
import imie.campus.security.services.AuthenticationService;

import javax.servlet.ServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.singleton;

/**
 * An AuthenticationService that logs every visitors as Administrator user.
 * Used only when the campus.security.disable parameter is set to true.
 * @author Fabien
 */
public class DebugAuthenticationServiceImpl implements AuthenticationService {

    private final RoleTemplate<Void> adminRole;

    private UserTemplate<?> adminUser;

    public DebugAuthenticationServiceImpl() {
        adminRole = new Role("ADMIN", new Role("MODERATOR", new Role("USER")));
        adminUser = new DummyAdminUser();
    }

    @Override public LoginResponse<?> login(Credentials creds)
            throws AuthenticationException
    {
        LoginResponseImpl<?> loginResponse = new LoginResponseImpl<>();
        loginResponse.setExpiration(Integer.MAX_VALUE);
        loginResponse.setToken("----Token---------------");
        loginResponse.setUsername("admin");

        return loginResponse;
    }

    @Override public Map<String, UserTemplate<?>> getSessions() { return null; }
    @Override public UserTemplate<?> getUserFromToken(String token) { return null; }
    @Override public boolean isTokenKnown(String token) { return false; }

    @Override public UserTemplate<?> checkAuthentication(ServletRequest req)
            throws AuthenticationFailedException { return adminUser; }

    @Override public void setEncrypter(Encrypter encrypter) {}
    @Override public Encrypter getEncrypter() { return null; }

    private class DummyAdminUser implements UserTemplate<String> {
        @Override public String getUsername() { return "admin"; }
        @Override public String getPassword() { return "*****"; }
        @Override public Optional<String> getSalt() { return Optional.empty(); }

        @Override public Set<? extends RoleTemplate<?>> getRoles() { return singleton(adminRole); }
        @Override public boolean isActive() { return true; }
    }

    private class Role implements RoleTemplate<Void>{
        private String key;
        private RoleTemplate<Void> parent;

        public Role(String key) {
            this.key = key;
        }

        public Role(String key, RoleTemplate<Void> parent) {
            this.key = key;
            this.parent = parent;
        }

        @Override public String getKey() { return key; }
        @Override public RoleTemplate<Void> getParent() { return parent; }
    }
}
