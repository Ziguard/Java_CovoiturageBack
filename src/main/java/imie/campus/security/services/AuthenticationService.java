package imie.campus.security.services;

import imie.campus.errors.exceptions._401.AuthenticationException;
import imie.campus.errors.exceptions._401.AuthenticationFailedException;
import imie.campus.security.encrypters.Encrypter;
import imie.campus.security.model.Credentials;
import imie.campus.security.model.LoginResponse;
import imie.campus.security.model.UserTemplate;

import javax.servlet.ServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * An authentication services, used as a central part of the security layer.
 * It has to perform login operation and to provide the current authenticated
 * user from a token.
 * @author Fabien
 */
public interface AuthenticationService {
    /**
     * Operates a user login.
     * @param creds The user credentials
     * @return A LoginResponse object that contains the token, the expiration date and the username
     * @throws AuthenticationException A authentication error has occured during the process
     */
    LoginResponse<?> login(@NotNull Credentials creds) throws AuthenticationException;

    /**
     * Returns the Map that stores all the connections.
     * @return Map of active connections
     */
    Map<String, UserTemplate<?>> getSessions();

    /**
     * Retrieves a user object by its connection token.
     * @param token The connection token
     * @return The UserTemplate instance matching to the token, or null if the token was not found
     */
    UserTemplate<?> getUserFromToken(String token);

    /**
     * Indicates whether the token is known by the security layer.
     * @param token The connection token to test
     * @return true if the token exists, false otherwise
     */
    boolean isTokenKnown(String token);

    /**
     * Performs an authentication checking from the HTTP request given as argument.
     *   Checks if a token is present, then parse the token and find the matching user object from it.
     * @param req The ServletRequest instance representing the request to check
     * @return A user object instance
     * @throws AuthenticationFailedException An error occured during the process
     */
    UserTemplate<?> checkAuthentication(ServletRequest req) throws AuthenticationFailedException;

    /**
     * Sets an encrypter to the services.
     * Used for test
     * @param encrypter The encrypter implementation
     */
    void setEncrypter(Encrypter encrypter);

    /**
     * Get the current services encrypter.
     * @return The encrypter implementation
     */
    Encrypter getEncrypter();
}
