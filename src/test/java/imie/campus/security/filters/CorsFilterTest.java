package imie.campus.security.filters;

import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class CorsFilterTest {

    static final String HK_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    static final String HK_ALLOW_METHODS = "Access-Control-Allow-Methods";
    static final String HK_MAX_AGE = "Access-Control-Max-Age";
    static final String HK_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    static final String HK_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    static final String HK_CACHE_CONTROL = "Cache-Control";

    private static CorsFilter corsFilter;

    @BeforeAll
    static void setUpSuite() {
        corsFilter = new CorsFilter();
    }

    @AfterAll static void tearDownSuite() {}
    @BeforeEach void setUp() {}
    @AfterEach void tearDown() {}

    @Test
    void doFilterAddingCorsHeadersOk() throws IOException, ServletException {
        MockFilterChain chain = new MockFilterChain();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response =  new MockHttpServletResponse();

        corsFilter.doFilter(request, response, chain);
        assertEquals("Authorization, Content-Type, enctype", response.getHeader(HK_ALLOW_HEADERS));
        assertEquals("*", response.getHeader(HK_ALLOW_ORIGIN));
        assertEquals("POST, GET, OPTIONS", response.getHeader(HK_ALLOW_METHODS));
        assertEquals("1000", response.getHeader(HK_MAX_AGE));
        assertEquals("Authorization", response.getHeader(HK_EXPOSE_HEADERS));
        assertEquals("private", response.getHeader(HK_CACHE_CONTROL));
    }

    /**
     * Appel de init().
     * @see CorsFilter#init(FilterConfig)
     */
    @Test
    public void testInit() {
        corsFilter.init(mock(FilterConfig.class));
    }

    /**
     * Appel de destroy().
     * @see CorsFilter#destroy()
     */
    @Test
    public void testDestroy() {
        corsFilter.destroy();
    }
}