package imie.campus.configuration;

import imie.campus.security.configuration.Configuration;
import imie.campus.security.filters.CorsFilter;
import imie.campus.security.filters.JWTAuthenticationFilter;
import imie.campus.security.services.AuthenticationService;
import imie.campus.security.services.DateTimeService;
import imie.campus.security.services.UserProvider;
import imie.campus.security.services.impl.AuthenticationServiceImpl;
import imie.campus.security.services.impl.DebugAuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import static imie.campus.utils.commons.GeneralUtils.booleanValue;

@org.springframework.context.annotation.Configuration
public class ApplicationConfig {
    /**
     * Security disabling parameter (debugging purpose)
     */
    @Value("${campus.security.disable}")
    private Boolean securityDisabled;

    /* Dependencies for AuthenticationService */
    private final UserProvider userProvider;
    private final DateTimeService dateTimeService;
    private final Configuration securityConfig;

    @Autowired
    public ApplicationConfig(UserProvider userProvider,
                    DateTimeService dateTimeService,
                    Configuration securityConfig) {
        this.userProvider = userProvider;
        this.dateTimeService = dateTimeService;
        this.securityConfig = securityConfig;
    }

    /**
     * Register special bean in the Spring context.
     * @return A FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        // Enabling JWTAuthenticationFilter only if security is enabled
        if (!booleanValue(securityDisabled)) {
            registrationBean.setFilter(new JWTAuthenticationFilter(authenticationService()));
        }
        // Registering CorsFilter to avoid CORS protection issues
        registrationBean.setFilter(new CorsFilter());
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Bean
    public AuthenticationService authenticationService() {
        return (!booleanValue(securityDisabled)) ?
                new AuthenticationServiceImpl(userProvider, dateTimeService, securityConfig) :
                new DebugAuthenticationServiceImpl();
    }
}
