package imie.campus.security.filters;

import imie.campus.errors.exceptions.RestException;
import imie.campus.errors.exceptions._401.AuthenticationFailedException;
import imie.campus.security.model.Restricted;
import imie.campus.security.model.UserTemplate;
import imie.campus.security.services.AuthenticationService;
import imie.campus.security.services.RoleCheckingService;
import imie.campus.security.services.RoleProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.ServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;

import static imie.campus.errors.exceptions._401.AuthenticationFailedException.R_EXPIRED_SESSION;
import static imie.campus.security.mocks.MockedRoles.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

class RestrictedFilterAdviceTest {
    /*
     * --- Données de tests ---
     */
    private static final String DUMMY_REASON = R_EXPIRED_SESSION;
    private static final String DUMMY_RESPONSE = "Ceci est ma réponse!";

    /*
     * Classe mockée (utile pour l'introspection)
     */
    class MockController {
        private Object object;
        public MockController(Object object) {
            this.object = object;
        }

        @Restricted("USER") Object method1() { return object; }
        @Restricted("MODERATOR") Object method2() { return object; }
        Object method3() { return object; }
    }

    @Restricted("ADMIN")
    class MockController2 {
        private Object object;
        public MockController2(Object object) {
            this.object = object;
        }

        Object method() { return object; }
    }

    class MockResponse {
        public String getResponse() { return DUMMY_RESPONSE; }
    }

    /**
     * Le filtre testé
     */
    static RestrictedFilterAdvice restrictedFilter;

    /*
     * --- Les dépendances ---
     */
    static AuthenticationService authService;

    static RoleCheckingService roleCheckingService;

    static RoleProvider roleProvider;

    @BeforeEach
    void setUp() {
        roleCheckingService = mock(RoleCheckingService.class);
        authService = mock(AuthenticationService.class);
        roleProvider = mock(RoleProvider.class);

        restrictedFilter = new RestrictedFilterAdvice(authService, roleCheckingService, roleProvider);
    }

    @AfterEach
    void tearDown() {
        restrictedFilter = null;
        authService = null;
        roleCheckingService = null;
        roleProvider = null;
    }

    /**
     * Test de support() avec une méthode marqué par l'annotation @Restricted
     * Attendu : renvoie true.
     * @see RestrictedFilterAdvice#supports(MethodParameter, Class)
     * @see imie.campus.security.model.Restricted
     */
    @Test
    @SuppressWarnings("unchecked")
    void supports_RestrictedAnnotatedMethod() throws Exception {
        Method method = MockController.class.getDeclaredMethod("method1");
        MethodParameter methodParameter = mock(MethodParameter.class);
        when(methodParameter.getMethod()).thenReturn(method);

        boolean actual = restrictedFilter.supports(methodParameter,
                (Class) HttpMessageConverter.class);

        assertTrue(actual);
    }

    /**
     * Test de support() avec une méthode non annoté par @Restricted
     * Attendu : renvoie false.
     * @see RestrictedFilterAdvice#supports(MethodParameter, Class)
     * @see imie.campus.security.model.Restricted
     */
    @Test
    @SuppressWarnings("unchecked")
    void supports_NotAnnotatedRestrictedMethod() throws Exception {
        Method method = MockController.class.getDeclaredMethod("method3");
        MethodParameter methodParameter = mock(MethodParameter.class);
        when(methodParameter.getMethod()).thenReturn(method);

        boolean actual = restrictedFilter.supports(methodParameter,
                (Class) HttpMessageConverter.class);

        assertFalse(actual);
    }

    /**
     * Test de support() avec un contrôleur annoté par @Restricted
     * Attendu : renvoie true.
     * @see RestrictedFilterAdvice#supports(MethodParameter, Class)
     * @see imie.campus.security.model.Restricted
     */
    @Test
    @SuppressWarnings("unchecked")
    void supports_AnnotatedRestrictedController() throws Exception {
        Method method = MockController2.class.getDeclaredMethod("method");
        MethodParameter methodParameter = mock(MethodParameter.class);
        when(methodParameter.getMethod()).thenReturn(method);

        boolean actual = restrictedFilter.supports(methodParameter,
                (Class) HttpMessageConverter.class);

        assertTrue(actual);
    }

    /**
     * Test de beforeBodyWrite() : Le services d'authentification a renvoyé
     *   une exception (AuthenticationException).
     * Attendu : retourne Une instance de RestError représentant l'exception
     *   levée.
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     * @see imie.campus.errors.exceptions._401.AuthenticationException
     */
    @Test
    @SuppressWarnings("unchecked")
    void beforeBodyWrite_AuthenticationException_MediaTypeJson() throws Exception {
        ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);
        ServletServerHttpResponse response = mock(ServletServerHttpResponse.class);

        Method method = MockController.class.getDeclaredMethod("method1");
        MethodParameter methodParameter = mock(MethodParameter.class);

        when(authService.checkAuthentication(request.getServletRequest()))
                .thenThrow(new AuthenticationFailedException(DUMMY_REASON));
        when(methodParameter.getMethod()).thenReturn(method);

        Object returns = restrictedFilter.beforeBodyWrite(
            new Object(), methodParameter, MediaType.APPLICATION_JSON,
                (Class) HttpMessageConverter.class, request, response);

        // Erreur retournée
        assertThat(returns, instanceOf(AuthenticationFailedException.class));
        AuthenticationFailedException exception = (AuthenticationFailedException) returns;

        // Assertions
        assertEquals(401, exception.getCode());
        assertEquals(DUMMY_REASON, exception.getReason());
        assertEquals(UNAUTHORIZED.getReasonPhrase(), exception.getStatus());
    }

    /**
     * Test de beforeBodyWrite() : Le services d'authentification a renvoyé
     *   une exception (AuthenticationException).
     * Attendu : retourne Une instance de RestError représentant l'exception
     *   levée.
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     * @see imie.campus.errors.exceptions._401.AuthenticationException
     */
    @Test
    @SuppressWarnings("unchecked")
    void beforeBodyWrite_AuthenticationException_MediaTypeOther() throws Exception {
        ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);
        ServletServerHttpResponse response = mock(ServletServerHttpResponse.class);

        Method method = MockController.class.getDeclaredMethod("method1");
        MethodParameter methodParameter = mock(MethodParameter.class);

        final AuthenticationFailedException authException = new AuthenticationFailedException(DUMMY_REASON);
        when(authService.checkAuthentication(request.getServletRequest()))
                .thenThrow(authException);
        when(methodParameter.getMethod()).thenReturn(method);

        Object returns = restrictedFilter.beforeBodyWrite(
                new Object(), methodParameter, MediaType.TEXT_PLAIN,
                (Class) HttpMessageConverter.class, request, response);

        // Assertions
        assertEquals(authException.getMessage(), returns.toString());
    }

    /**
     * Test de beforeBodyWrite() : L'utilisateur n'a pas les droits suffisant
     *   pour accéder à une route du WebService.
     * Attendu : retourne Une instance de RestError représentant l'exception
     *   levée.
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     * @see imie.campus.errors.exceptions._403.ForbiddenAccessException
     */
    @Test
    @SuppressWarnings("unchecked")
    void beforeBodyWrite_NoSufficientRight() throws Exception {
        ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);
        ServletServerHttpResponse response = mock(ServletServerHttpResponse.class);

        Method method = MockController.class.getDeclaredMethod("method2");
        MethodParameter methodParameter = mock(MethodParameter.class);

        UserTemplate user = mock(UserTemplate.class);
        when(authService.checkAuthentication(request.getServletRequest()))
                .thenReturn(user);
        when(methodParameter.getMethod()).thenReturn(method);
        when(user.getRoles()).thenReturn(Collections.emptySet());
        when(roleCheckingService.matches(user.getRoles(), "ADMIN")).thenReturn(false);
        when(roleProvider.getRoles()).thenReturn(Arrays.asList(admin(), moderator(), user()));

        Object returns = restrictedFilter.beforeBodyWrite(
                new Object(), methodParameter, MediaType.APPLICATION_JSON,
                (Class) HttpMessageConverter.class, request, response);

        // Erreur retournée
        assertThat(returns, instanceOf(RestException.class));
        RestException exception = (RestException) returns;

        // Assertions
        assertEquals(403, exception.getCode());
        assertEquals(FORBIDDEN.getReasonPhrase(), exception.getStatus());
    }

    /**
     * Test de beforeBodyWrite() : L'utilisateur peut accéder à la route demandée.
     * Attendu : sL'objet passé à la méthode est renvoyé tel quel
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     */
    @Test
    @SuppressWarnings("unchecked")
    void beforeBodyWrite_AccessGranted() throws Exception {
        ServletServerHttpRequest request = mock(ServletServerHttpRequest.class);
        ServletServerHttpResponse response = mock(ServletServerHttpResponse.class);

        Method method = MockController.class.getDeclaredMethod("method2");
        MethodParameter methodParameter = mock(MethodParameter.class);
        UserTemplate user = mock(UserTemplate.class);

        when(authService.checkAuthentication(request.getServletRequest())).thenReturn(user);
        when(methodParameter.getMethod()).thenReturn(method);
        // La vérification des rôles est toujours un succès
        when(roleCheckingService.matches(anySet(), anyString())).thenReturn(true);
        when(roleProvider.getRoles()).thenReturn(Arrays.asList(admin(), moderator(), user()));

        MockResponse mockResponse = new MockResponse();
        Object returns = restrictedFilter.beforeBodyWrite(
                mockResponse, methodParameter, MediaType.APPLICATION_JSON,
                (Class) HttpMessageConverter.class, request, response);

        // Assertions
        assertEquals(mockResponse, returns);
        assertThat(returns, instanceOf(MockResponse.class));
        assertEquals(DUMMY_RESPONSE, ((MockResponse) returns).getResponse());
    }

}