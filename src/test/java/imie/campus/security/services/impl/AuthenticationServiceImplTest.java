package imie.campus.security.services.impl;

import imie.campus.errors.exceptions._401.*;
import imie.campus.security.configuration.Configuration;
import imie.campus.security.encrypters.Encrypter;
import imie.campus.security.model.Credentials;
import imie.campus.security.model.LoginResponse;
import imie.campus.security.model.UserTemplate;
import imie.campus.security.services.AuthenticationService;
import imie.campus.security.services.DateTimeService;
import imie.campus.security.services.UserProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.ServletRequest;
import java.util.Date;

import static imie.campus.errors.exceptions._401.AuthenticationFailedException.*;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * AuthenticationService test class
 * @see AuthenticationService
 * @author Fabien
 */
@SuppressWarnings("unchecked")
class AuthenticationServiceImplTest {

    /*
     * --- Constants : test data ---
     */
    private static final String EMPTY = "";
    private static final String DUMMY_USERNAME = "$$$";
    private static final String DUMMY_USERNAME_2 = "###";
    private static final String DUMMY_PASSWORD = "########";
    private static final String DUMMY_PASSWORD_2 = "********";
    private static final Date DUMMY_TIMESTAMP_NOW = new Date(1514761200L);
    private static final Date DUMMY_TIMESTAMP_EXP = new Date(1514847600L);
    private static final String DUMMY_SECRET = "Rgk8$zQ:!";
    private static final String DUMMY_SECRET_2 = "$m3^Ml86@";
    private static final long DUMMY_TTL = 86400;
    private static final Object DUMMY_AUTHORIZATION = "<authorization.value>" ;

    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = HS256;

    /**
     * The instance to test
     */
    static AuthenticationService authService;

    /*
     * --- Dépendencies ---
     */

    /**
     * User provider
     */
    static UserProvider userProvider;

    /**
     * Date computing service
     */
    static DateTimeService dateTimeService;

    /**
     * Security configuration
     */
    static Configuration securityConfig;

    /**
     * Before each test case, we mock the dependency and create a new instance.
     */
    @BeforeEach
    void setUp() {
        userProvider = mock(UserProvider.class);
        dateTimeService = mock(DateTimeService.class);
        securityConfig = mock(Configuration.class);
        authService = spy(
                new AuthenticationServiceImpl(userProvider, dateTimeService, securityConfig));
    }

    /**
     * After each test case, freeing all dependencies
     */
    @AfterEach
    void tearDown() {
        dateTimeService = null;
        authService = null;
        userProvider = null;
    }

    /**
     * Username = empty string
     * Expected : raise a InvalidCredentialsException
     * @see AuthenticationService#login(Credentials)
     */
    @Test
    void login_InvalidCredentialsEmptyUsername() {
        Credentials creds = mock(Credentials.class);
        when(creds.getUsername()).thenReturn(EMPTY);
        when(creds.getPassword()).thenReturn(DUMMY_USERNAME);

        assertThrows(InvalidCredentialsException.class, () ->
                authService.login(creds));
    }

    /**
     * Password = empty string
     * Expected : Raise a InvalidCredentialsException
     * @see AuthenticationService#login(Credentials)
     */
    @Test
    void login_InvalidCredentialsEmptyPassword() {
        Credentials creds = mock(Credentials.class);
        when(creds.getUsername()).thenReturn(DUMMY_USERNAME);
        when(creds.getPassword()).thenReturn(EMPTY);

        assertThrows(InvalidCredentialsException.class, () ->
                authService.login(creds));
    }

    /**
     * No user found in database.
     * Expected : Raise a UnknownUserException
     * @see AuthenticationService#login(Credentials)
     */
    @Test
    void login_UserNotFound() {
        Credentials creds = mock(Credentials.class);
        when(creds.getUsername()).thenReturn(DUMMY_USERNAME);
        when(creds.getPassword()).thenReturn(DUMMY_PASSWORD);

        when(userProvider.findByUsername(DUMMY_USERNAME)).thenReturn(null);

        assertThrows(UnknownUserException.class, () ->
                authService.login(creds));
    }

    /**
     * Inactive user
     * Expected : raise a InactiveUserException
     * @see AuthenticationService#login(Credentials)
     */
    @Test
    @SuppressWarnings("unchecked")
    void login_InactiveUser() {
        Credentials creds = mock(Credentials.class);
        when(creds.getUsername()).thenReturn(DUMMY_USERNAME);
        when(creds.getPassword()).thenReturn(DUMMY_PASSWORD);

        Encrypter<String> encrypter = mock(Encrypter.class);

        UserTemplate user = mock(UserTemplate.class);
        when(userProvider.findByUsername(DUMMY_USERNAME)).thenReturn(user);
        when(user.getPassword()).thenReturn(DUMMY_PASSWORD);

        when(encrypter.supports(any())).thenReturn(true);
        when(encrypter.compare(anyString(), anyString())).thenReturn(true);
        authService.setEncrypter(encrypter);

        when(user.isActive()).thenReturn(false);

        assertThrows(InactiveUserException.class, () ->
                authService.login(creds));
    }

    /**
     * Wrong password
     * Expected : raise a InvalidPasswordException
     * @see AuthenticationService#login(Credentials)
     */
    @Test
    @SuppressWarnings("unchecked")
    void login_UserInvalidPassword() {
        Credentials creds = mock(Credentials.class);
        when(creds.getUsername()).thenReturn(DUMMY_USERNAME);
        when(creds.getPassword()).thenReturn(DUMMY_PASSWORD);

        Encrypter<String> encrypter = mock(Encrypter.class);

        UserTemplate user = mock(UserTemplate.class);
        when(userProvider.findByUsername(DUMMY_USERNAME)).thenReturn(user);
        when(user.getPassword()).thenReturn(DUMMY_PASSWORD_2);

        when(encrypter.supports(any())).thenReturn(true);
        when(encrypter.compare(anyString(), anyString())).thenReturn(false);
        authService.setEncrypter(encrypter);

        assertThrows(InvalidPasswordException.class, () ->
                authService.login(creds));
    }

    /**
     * An helper method to generate JWT token
     * @param subject The subject
     * @param issuedAt The issued at date
     * @param expiration The expiration date
     * @param signatureAlgorithm The algorithm
     * @param signatureSecretKey The secret key
     * @return The token
     */
    static String jwtToken(final String subject,
                             final Date issuedAt,
                             final Date expiration,
                             final SignatureAlgorithm signatureAlgorithm,
                             final String signatureSecretKey) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(signatureAlgorithm, signatureSecretKey)
                .compact();
    }

    /**
     * Checking generation and storage of token/user tuple in the service.
     * Expected :
     *   - token generated by io.jsonwebtoken
     *   - getSessions() size = 1
     *   - getSessions[0].key = token
     *   - getSessions[0].value = Mock(User) {
     *   username = DUMMY_USERNAME,
     *   password = DUMMY_PASSWORD,
     *   }
     * @see AuthenticationService#login(Credentials)
     */
    @Test
    @SuppressWarnings("unchecked")
    void login_TokenBuildingAndStorage() {
        // Additionnal mocks
        Credentials creds = mock(Credentials.class);

        // Injecting mocked values
        when(creds.getUsername()).thenReturn(DUMMY_USERNAME);
        when(creds.getPassword()).thenReturn(DUMMY_PASSWORD);

        UserTemplate user = mock(UserTemplate.class);
        when(userProvider.findByUsername(DUMMY_USERNAME)).thenReturn(user);
        when(user.getPassword()).thenReturn(DUMMY_PASSWORD);
        when(user.isActive()).thenReturn(true);

        Encrypter encrypter = mock(Encrypter.class);
        when(encrypter.supports(any())).thenReturn(true);
        when(encrypter.compare(any(), any())).thenReturn(true);

        when(dateTimeService.now()).thenReturn(DUMMY_TIMESTAMP_NOW);
        when(dateTimeService.expiration()).thenReturn(DUMMY_TIMESTAMP_EXP);
        when(securityConfig.secretKey()).thenReturn(DUMMY_SECRET);
        when(securityConfig.timeToLeave()).thenReturn(DUMMY_TTL);
        when(securityConfig.signatureAlgorithm()).thenReturn(SIGNATURE_ALGORITHM);
        authService.setEncrypter(encrypter);

        // Generating token with io.jsonwebtoken
        final String expectedToken = jwtToken(DUMMY_USERNAME,
                DUMMY_TIMESTAMP_NOW, DUMMY_TIMESTAMP_EXP,
                SIGNATURE_ALGORITHM, DUMMY_SECRET);

        // Calling of target service with mocked data
        LoginResponse loginResponse = authService.login(creds);
        assertEquals(loginResponse.getToken(), expectedToken);
        assertEquals(new Date(loginResponse.getExpiration()), DUMMY_TIMESTAMP_EXP);
        assertEquals(loginResponse.getUsername(), user.getUsername());

        // Checking the adding of the session
        assertEquals(1, authService.getSessions().size());
        String storedToken = authService.getSessions().keySet()
                .iterator().next();
        assertEquals(expectedToken, storedToken);
        UserTemplate storedUser = authService.getUserFromToken(storedToken);
        assertEquals(user, storedUser);
    }

    /**
     * Checking the send of a technical error when the used encrypter 
     * does not support the password type.
     * Requirements :
     *   - Type of password to test in an instance of Class<?>
     *   - The method Encrypter::supports() returns false
     * Expected : an TechnicalException is thrown
     * @see AuthenticationService#login(Credentials)
     */
    @Test
    @SuppressWarnings("unchecked")
    void login_UnsupportedPasswordType() {
        // Additionnal mocks
        Credentials creds = mock(Credentials.class);

        // Injecting mocked values
        when(creds.getUsername()).thenReturn(DUMMY_USERNAME);
        when(creds.getPassword()).thenReturn(DUMMY_PASSWORD);

        UserTemplate user = mock(UserTemplate.class);
        when(userProvider.findByUsername(DUMMY_USERNAME)).thenReturn(user);
        when(user.getPassword()).thenReturn(DUMMY_PASSWORD);

        Encrypter encrypter = mock(Encrypter.class);
        when(encrypter.supports(any())).thenReturn(false);
        authService.setEncrypter(encrypter);

        assertThrows(UnsupportedOperationException.class, () -> authService.login(creds));
    }

    /**
     * Checking the authentication : header Authorization is null
     * Expected :
     *   - raise of an AuthentificationFailedException
     *   - reason() = AuthentificationFailedException::R_MISSING_AUTHORIZATION
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     */
    @Test
    void checkAuth_NullAuthorization() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        assertThrows(AuthenticationException.class, () ->
                authService.checkAuthentication(request));

        try {
            authService.checkAuthentication(request);
        } catch (AuthenticationFailedException e) {
            assertEquals(R_MISSING_AUTHORIZATION, e.getReason());
        }
    }

    /**
     * Checking the authentication : format of header Authorization 
     *   is wrong.
     * Expected : 
     *   - raise of an AuthentificationFailedException
     *   - reason() = AuthentificationFailedException::R_MISSING_AUTHORIZATION
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     */
    @Test
    void checkAuth_InvalidAuthorization() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", DUMMY_AUTHORIZATION);

        assertThrows(AuthenticationException.class, () ->
                authService.checkAuthentication(request));

        try {
            authService.checkAuthentication(request);
        } catch (AuthenticationFailedException e) {
            assertEquals(R_MISSING_AUTHORIZATION, e.getReason());
        }
    }

    /**
     * Checking the authentication : the token is invalid (expiration)
     * Expected : 
     *   - raise of an AuthentificationFailedException
     *   - reason() = AuthentificationFailedException::R_INVALID_TOKEN
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     */
    @Test
    void checkAuth_ExpiredToken() {
        // Generating a token expired since one day
        String dummyToken = jwtToken(DUMMY_USERNAME,
            new Date(System.currentTimeMillis() - 86400 * 2),
            new Date(System.currentTimeMillis() - 86400),
            SIGNATURE_ALGORITHM, DUMMY_SECRET);

        // Request mocking
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + dummyToken);

        // Mocking configuration (secrey key)
        when(securityConfig.secretKey()).thenReturn(DUMMY_SECRET);

        assertThrows(AuthenticationException.class, () ->
                authService.checkAuthentication(request));

        try {
            authService.checkAuthentication(request);
        } catch (AuthenticationFailedException e) {
            assertEquals(R_INVALID_TOKEN, e.getReason());
        }
    }

    /**
     * Checking the authentication : the session is expired or
     *   the user identified in hte request in not authenticated
     * Expected : 
     *   - raise of an AuthentificationFailedException
     *   - reason() = AuthentificationFailedException::R_EXPIRED_SESSION
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     */
    @Test
    void checkAuth_SessionExpired() {
        // Token generation
        String dummyToken = jwtToken(DUMMY_USERNAME_2,
                new Date(),
                new Date(System.currentTimeMillis() + 86400),
                SIGNATURE_ALGORITHM, DUMMY_SECRET);

        // Request mocking
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + dummyToken);

        // Mocking configuration (secret key)
        when(securityConfig.secretKey()).thenReturn(DUMMY_SECRET);

        assertThrows(AuthenticationException.class, () ->
                authService.checkAuthentication(request));

        try {
            authService.checkAuthentication(request);
        } catch (AuthenticationFailedException e) {
            assertEquals(R_EXPIRED_SESSION, e.getReason());
        }
    }

    /**
     * Checking the authentication : the secret key used to generate the token
     *   does not match with one provided by the configuration.
     * Expected : 
     *   - raise of an AuthentificationFailedException
     *   - reason() = AuthentificationFailedException::R_INVALID_TOKEN
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     */
    @Test
    void checkAuth_MismatchSigningKey() {
        // Generating a token
        String dummyToken = jwtToken(DUMMY_USERNAME,
                new Date(),
                new Date(System.currentTimeMillis() + 86400),
                SIGNATURE_ALGORITHM, DUMMY_SECRET);

        // Request mocking
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + dummyToken);

        // Mocking configuration and current user
        UserTemplate user = mock(UserTemplate.class);
        when(user.getUsername()).thenReturn(DUMMY_USERNAME);

        when(authService.isTokenKnown(dummyToken)).thenReturn(true);
        when(authService.getUserFromToken(dummyToken)).thenReturn(user);

        when(securityConfig.secretKey()).thenReturn(DUMMY_SECRET_2);

        assertThrows(AuthenticationException.class, () ->
                authService.checkAuthentication(request));

        try {
            authService.checkAuthentication(request);
        } catch (AuthenticationFailedException e) {
            assertEquals(R_INVALID_TOKEN, e.getReason());
        }
    }

    /**
     * Checking the authentication : the secret key used to generate the token
     *   does not match with one provided by the configuration.
     * Expected : 
     *   - raise of an AuthentificationFailedException
     *   - reason() = AuthentificationFailedException::R_INVALID_TOKEN
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     */
    @Test
    void checkAuth_InvalidToken() {
        // Generating a token
        String dummyToken = "Q2VjaSBuJ2VzdCBwYXMgdW4gSldU3.RG9tbWFnZQ==.MjAxOC0wMi0xNQ==";

        // Request mocking
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + dummyToken);

        // Mocking configuration and current user
        UserTemplate user = mock(UserTemplate.class);
        when(user.getUsername()).thenReturn(DUMMY_USERNAME);

        when(authService.isTokenKnown(dummyToken)).thenReturn(true);
        when(authService.getUserFromToken(dummyToken)).thenReturn(user);

        when(securityConfig.secretKey()).thenReturn(DUMMY_SECRET);

        assertThrows(AuthenticationException.class, () ->
                authService.checkAuthentication(request));

        try {
            authService.checkAuthentication(request);
        } catch (AuthenticationFailedException e) {
            assertEquals(R_INVALID_TOKEN, e.getReason());
        }
    }

    /**
     * Checking the authentication : authentification OK
     * Expected : 
     *   - return: the token
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     */
    @Test
    void checkAuth_Ok() {
        // Generating a token
        String dummyToken = jwtToken(DUMMY_USERNAME,
                new Date(),
                new Date(System.currentTimeMillis() + 86400),
                SIGNATURE_ALGORITHM, DUMMY_SECRET);

        // Request mocking
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + dummyToken);

        // Mocking configuration and current user
        UserTemplate user = mock(UserTemplate.class);
        when(user.getUsername()).thenReturn(DUMMY_USERNAME);

        when(authService.isTokenKnown(dummyToken)).thenReturn(true);
        when(authService.getUserFromToken(dummyToken)).thenReturn(user);
        when(securityConfig.secretKey()).thenReturn(DUMMY_SECRET);

        assertEquals(user, authService.checkAuthentication(request));
    }

    @Test
    void getEncrypter_Ok() {
        Encrypter<String> mockEncrypter = mock(Encrypter.class);
        authService.setEncrypter(mockEncrypter);

        assertEquals(authService.getEncrypter(), mockEncrypter);
    }
}