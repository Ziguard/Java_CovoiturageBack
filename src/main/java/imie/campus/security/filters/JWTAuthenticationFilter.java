package imie.campus.security.filters;

import imie.campus.errors.exceptions._401.AuthenticationFailedException;
import imie.campus.security.services.AuthenticationService;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static imie.campus.utils.commons.GeneralUtils.toJson;

/**
 * A servlet filter that checks that every request to a protected-access route has
 * correct authentication token and required access rights.
 * @author Fabien
 */
public class JWTAuthenticationFilter implements Filter {
    /**
     * Represents the HTTP verb "OPTIONS"
     */
    private final static String HTTP_OPTIONS = "OPTIONS";

    /**
     * The authentication services
     */
    private final AuthenticationService authService;

    /**
     * Filter instanciation, with DI via this constructor.
     * @param authService An AuthenticationService instance
     */
    public JWTAuthenticationFilter(AuthenticationService authService) {
        if (authService == null) {
            throw new IllegalArgumentException("L'instance de AuthenticationService passée au filtre ne doit pas être nulle.");
        }
        this.authService = authService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;

        try {
            if (HTTP_OPTIONS.equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                authService.checkAuthentication(req);
            }
            chain.doFilter(req, res);
        } catch (AuthenticationFailedException e) {
            // JSON Error message generation if an authentication error occurs.
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(toJson(e));
            response.setStatus(e.getCode());
        }
    }

    public void init(FilterConfig config) {}
    public void destroy() {}
}
