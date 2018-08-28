package imie.campus.security.controllers;

import imie.campus.security.model.Credentials;
import imie.campus.security.model.LoginResponse;
import imie.campus.security.services.AuthenticationService;

/**
 * The controller that expose login route to permit
 *   authentication from the client front-end.
 * @author Fabien
 */
public abstract class AbstractLoginController {
    /**
     * Authentication services
     */
    private final AuthenticationService authenticationService;

    /**
     * Controller constructor
     * @param authenticationService Spring injected instance for the authentication services.
     */
    protected AbstractLoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Login process entry point method
     * @param creds The credentials sent by the client
     * @return JSON request result
     */
    protected final LoginResponse<?> performLogin(Credentials creds) {
        return authenticationService.login(creds);
    }

    abstract public LoginResponse<?> login(Credentials creds);
}
