package imie.campus.security.filters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import imie.campus.errors.exceptions._401.AuthenticationFailedException;
import imie.campus.security.model.UserTemplate;
import imie.campus.security.services.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static imie.campus.errors.exceptions._401.AuthenticationFailedException.R_EXPIRED_SESSION;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class JWTAuthenticationFilterTest {

    /*
     * --- Constantes ---
     */
    private static final String HTTP_OPTIONS = "OPTIONS";

    /*
     * --- Données de test ---
     */
    private static final String DUMMY_RESPONSE_BODY = "---- Corps du message\n\n ----";

    /**
     * Le filtre testé
     */
    static JWTAuthenticationFilter jwtFilter;

    /*
     * --- Les dépendances ---
     */
    static AuthenticationService authService;

    @BeforeEach
    void setUp() {
        authService = mock(AuthenticationService.class);
        jwtFilter = new JWTAuthenticationFilter(authService);
        jwtFilter = spy(jwtFilter);
    }

    @AfterEach
    void tearDown() {
        jwtFilter = null;
        authService = null;
    }

    /**
     * Appel du constructeur avec null
     * Attendu : levée d'une exception IllegalArgumentException
     * @see JWTAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Test
    void construct_NullAuthService() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new JWTAuthenticationFilter(null));
    }

    /**
     * Appel de doFilter() avec une requête dont la méthode HTTP = OPTIONS
     * Attendu : le statut de la réponse (response.getStatus()) = 200
     * @see JWTAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Test
    void doFilter_HTTPOptions_ResponseOK() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        request.setMethod(HTTP_OPTIONS);
        jwtFilter.doFilter(request, response, chain);

        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
    }

    /**
     * Appel de doFilter() avec une requête avec checkAuthentication() qui retourne true
     * Attendu : le corps de la réponse est laissé tel quel
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     * @see JWTAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Test
    @SuppressWarnings("unchecked")
    void doFilter_checkAuthenticationOK() throws Exception {
        // Instanciation des mocks
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // Définition des valeurs
        response.getOutputStream().println(DUMMY_RESPONSE_BODY);
        UserTemplate user = mock(UserTemplate.class);
        when(authService.checkAuthentication(request)).thenReturn(user);

        // Appel de la méthode à tester
        jwtFilter.doFilter(request, response, chain);

        // Assertions
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        final String content = stripEndingNewLineCharacters(response.getContentAsString());
        assertThat(content, equalTo(DUMMY_RESPONSE_BODY));
    }

    /**
     * Appel de doFilter() avec une requête avec checkAuthentication() qui lève une
     *   exception.
     * Attendu : le corps de la réponse est modifié => JSON de l'erreur déclenché
     * @see AuthenticationService#checkAuthentication(ServletRequest)
     * @see JWTAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Test
    void doFilter_checkAuthenticationFailed() throws Exception {
        // Instanciation des mocks
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        // Définition des valeurs
        when(authService.checkAuthentication(request))
                .thenThrow(new AuthenticationFailedException(R_EXPIRED_SESSION));

        // Appel de la méthode à tester
        jwtFilter.doFilter(request, response, chain);

        // Assertions
        assertEquals(SC_UNAUTHORIZED, response.getStatus());
        final String content = response.getContentAsString();

        TypeReference<Map<String,Object>> typeRef
                = new TypeReference<Map<String,Object>>() {};

        Map<String, Object> error = new ObjectMapper().readValue(
                content, typeRef);

        assertEquals(401, error.get("code"));
        assertEquals("Unauthorized", error.get("status"));
        assertEquals(AuthenticationFailedException.EXCEPTION_MESSAGE, error.get("message"));
        assertEquals(R_EXPIRED_SESSION, error.get("reason"));
    }

    /**
     * Appel de init().
     * @see CorsFilter#init(FilterConfig)
     */
    @Test
    public void testInit() {
        jwtFilter.init(mock(FilterConfig.class));
    }

    /**
     * Appel de destroy().
     * @see CorsFilter#destroy()
     */
    @Test
    public void testDestroy() {
        jwtFilter.destroy();
    }

    /*
     * --- Méthodes utilitaires
     */

    /**
     * Supprime les caractères spéciaux de saut de ligne \n, \r et les espaces blancs
     *   à la fin du contenu de la chaîne de caractères passée en paramètre.
     * @param source La chaîne à traiter
     * @return La chaîne traitée
     */
    static String stripEndingNewLineCharacters(final String source) {
        return source.replaceAll("[\\n\\r ]+$", "");
    }
}