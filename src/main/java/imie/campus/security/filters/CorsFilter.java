package imie.campus.security.filters;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A servlet filter that add Cross-Origin headers to prevent request from
 * being filtered by CORS browser protection.
 * @author Fabien
 */
public class CorsFilter implements Filter {

    public void init(FilterConfig filterConfig) {}
    public void destroy() {}

    /**
     * Add Cross Origin headers.
     * {@inheritDoc}
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletResponse response = (HttpServletResponse) res;
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.addHeader("Access-Control-Max-Age", "1000");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, enctype");
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Cache-Control", "private");

        chain.doFilter(req, res);
    }


}
