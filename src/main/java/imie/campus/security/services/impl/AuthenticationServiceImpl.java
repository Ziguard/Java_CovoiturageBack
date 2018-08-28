package imie.campus.security.services.impl;

import imie.campus.errors.exceptions._401.*;
import imie.campus.security.configuration.Configuration;
import imie.campus.security.encrypters.Encrypter;
import imie.campus.security.model.Credentials;
import imie.campus.security.model.LoginResponse;
import imie.campus.security.model.UserTemplate;
import imie.campus.security.model.impl.LoginResponseImpl;
import imie.campus.security.services.AuthenticationService;
import imie.campus.security.services.DateTimeService;
import imie.campus.security.services.UserProvider;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static imie.campus.errors.exceptions._401.AuthenticationFailedException.*;
import static imie.campus.utils.commons.GeneralUtils.isEmpty;
import static imie.campus.utils.commons.GeneralUtils.nonEmpty;


public class AuthenticationServiceImpl implements AuthenticationService {
    /**
     * The logger
     */
    final Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * The constants
     */
    private final static String BEARER = "Bearer ";
    private final static String AUTHORIZATION = "Authorization";

    /**
     * A services that provides application users
     */
    private final UserProvider userProvider;

    /**
     * A services that manages and computes dates
     */
    private final DateTimeService dateTimeService;

    /**
     * The security configuration
     */
    private final Configuration securityConfig;

    /**
     * The password encrypter
     */
    private Encrypter encrypter;

    /**
     * A Map that stores registered sessions.
     */
    private final Map<String, UserTemplate<?>> sessions = new HashMap<>();

    /**
     * Construct an instance.
     * @param userProvider Le services DAO des utilisateurs
     * @param dateTimeService Le services de calcul de dates
     * @param securityConfig La configuration de la sécurité
     */
    public AuthenticationServiceImpl(UserProvider userProvider,
                                     DateTimeService dateTimeService,
                                     Configuration securityConfig) {
        this.userProvider = userProvider;
        this.dateTimeService = dateTimeService;
        this.securityConfig = securityConfig;
        this.encrypter = securityConfig.encrypter();
    }

    @Override
    @SuppressWarnings("unchecked")
    public LoginResponse login(@NotNull final Credentials creds) throws AuthenticationException {

        // Checking that credentials are complete
        if (!nonEmpty(creds.getUsername(), creds.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // Searching matching user
        UserTemplate user = userProvider.findByUsername(creds.getUsername());

        if (user == null) {
            throw new UnknownUserException();
        }

        // Checking that the encrypter supports the given password
        if (!encrypter.supports(creds.getPassword().getClass())) {
            throw new UnsupportedOperationException("The encryption algorithm does not support passwords of this type.");
        }

        // Checking the password matches with stored hash
        if (!encrypter.compare(creds.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        // Checking that the user is active
        if (!user.isActive()) {
            throw new InactiveUserException();
        }

        Date expiration = dateTimeService.expiration();

        // JWT generation
        String token = Jwts.builder()
                .setSubject(creds.getUsername())
                .setIssuedAt(dateTimeService.now())
                .setExpiration(expiration)
                .signWith(
                        securityConfig.signatureAlgorithm(),
                        securityConfig.secretKey())
                .compact();

        // Storing the session
        sessions.put(token, user);

        // We send also the username
        return buildSession(expiration, token, user.getUsername());
    }

    /**
     * Build a LoginResponse object from the given arguments.
     * @param expiration The token expiration date
     * @param token The token
     * @param username The username
     * @return A LoginResponse object representing the connecting session of the user
     */
    private static LoginResponse<?> buildSession(Date expiration, String token, String username) {
        LoginResponseImpl<?> loginResponse = new LoginResponseImpl<>();
        loginResponse.setExpiration(expiration.getTime());
        loginResponse.setToken(token);
        loginResponse.setUsername(username);

        return loginResponse;
    }

    @Override
    public Map<String, UserTemplate<?>> getSessions() {
        return sessions;
    }

    @Override
    public UserTemplate<?> getUserFromToken(final String token) {
        return sessions.get(token);
    }

    @Override
    public boolean isTokenKnown(final String token) {
        return sessions.containsKey(token) && sessions.get(token) != null;
    }

    @Override
    public UserTemplate<?> checkAuthentication(ServletRequest req) throws AuthenticationFailedException {
        // Retrieving the token from the headers
        final HttpServletRequest request = (HttpServletRequest) req;
        final String authHeader = request.getHeader(AUTHORIZATION);

        // Checkign if the right header is present and contains a token
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            throw new AuthenticationFailedException(R_MISSING_AUTHORIZATION);
        }

        final String token = authHeader.replaceAll(BEARER, "");

        try {
            // Parsing the token
            String subject = Jwts.parser()
                    .setSigningKey(securityConfig.secretKey())
                    .parseClaimsJws(token).getBody().getSubject();

            // If there is no username stored in the token
            // OR if the token is not known by the services
            if (isEmpty(subject) || !this.isTokenKnown(token)) {
                throw new AuthenticationFailedException(R_EXPIRED_SESSION);
            } else {
                // Returning the user object associated to the token
                return getUserFromToken(token);
            }
        } catch (JwtException e) {
            // Removing the expired token from the services
            if (this.isTokenKnown(token)) {
                sessions.remove(token);
            }
            // When an error during the token parsing occurs
            logger.debug("Token-based authentication has failed : {}, {}",
                    e.getClass().getSimpleName(), e.getLocalizedMessage());
            // Throwing a more generic exception
            throw new AuthenticationFailedException(R_INVALID_TOKEN);
        }
    }

    @Override
    public void setEncrypter(final Encrypter encrypter) {
        this.encrypter = encrypter;
    }

    @Override
    public Encrypter getEncrypter() {
        return encrypter;
    }
}
