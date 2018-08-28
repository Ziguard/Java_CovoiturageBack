package imie.campus.security.filters;

import imie.campus.errors.exceptions._401.AuthenticationException;
import imie.campus.errors.exceptions._403.ForbiddenAccessException;
import imie.campus.security.model.Restricted;
import imie.campus.security.model.RoleTemplate;
import imie.campus.security.model.UserTemplate;
import imie.campus.security.services.AuthenticationService;
import imie.campus.security.services.RoleCheckingService;
import imie.campus.security.services.RoleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static imie.campus.utils.commons.GeneralUtils.firstDefinedAmong;

/**
 * This class can intercept request-mapped methods in controllers that are
 * annotated with @Restricted.
 * Then it checks that the current authenticated user got the sufficient rights.
 * If it is not the case, it modifies the response to send a JSON representation of
 *   the error instead.
 * @author Fabien
 * @see ResponseBodyAdvice
 */
@ControllerAdvice
public class RestrictedFilterAdvice implements ResponseBodyAdvice<Object> {

    /**
     * The authentication services
     */
    private final AuthenticationService authService;

    /**
     * The role checking services
     */
    private final RoleCheckingService roleCheckingService;

    /**
     * A services that provides the list of supported roles
     */
    private final RoleProvider roleProvider;

    /**
     * A cached list of the registered roles
     */
    private final Map<String, RoleTemplate<?>> registeredRoles = new HashMap<>();

    /**
     * A property that can be used to inhibit the security layer
     */
    @Value("${campus.security.disable}")
    private Boolean securityDisabled;

    /**
     * The filter constructor, which injects the required services instances.
     * @param authService The authentication services
     * @param roleCheckingService The role checking services
     * @param roleProvider The role provider
     */
    @Autowired
    public RestrictedFilterAdvice(AuthenticationService authService,
                                  RoleCheckingService roleCheckingService,
                                  RoleProvider roleProvider) {
        this.authService = authService;
        this.roleCheckingService = roleCheckingService;
        this.roleProvider = roleProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // skipping if security is disabled
        if (securityDisabled != null && securityDisabled)
            return false;

        // The ResponseBodyFilter is enabled only if the target method is annotated with @Restricted
        final Method target = returnType.getMethod();
        return  target.isAnnotationPresent(Restricted.class) ||
                target.getDeclaringClass().isAnnotationPresent(Restricted.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response)
    {
        // The controller method
        final Method target = returnType.getMethod();
        
        // Retrieving @Restricted metadata from the route method or the controller class
        Restricted restricted = firstDefinedAmong(
                target.getAnnotation(Restricted.class),
                target.getDeclaringClass().getAnnotation(Restricted.class));

        // Retrieving the current authenticated user
        try {
            UserTemplate<?> user = authService.checkAuthentication(
                    ((ServletServerHttpRequest) request).getServletRequest());

            // Retrieving the list of roles from the role provider to the registered role cache
            if (registeredRoles.isEmpty()) {
                registeredRoles.putAll(roleProvider.getRoles().stream()
                        .collect(
                                Collectors.toMap(RoleTemplate::getKey, Function.identity())
                        ));
            }

            // Computing the hierarchy of all roles specified in @Restricted
            Set<RoleTemplate<?>> authorizedRoles = Stream.of(restricted.value())
                    .map(registeredRoles::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // If none user role matches with required roles for the current request
            if (authorizedRoles.stream().noneMatch(
                    role -> roleCheckingService.matches(user.getRoles(), role.getKey()) ))
            {
                throw new ForbiddenAccessException();
            }
        } catch (ForbiddenAccessException | AuthenticationException e) {
            // Generating JSON error message and attributing HTTP response status code
            response.setStatusCode(e.status());

            // Returning response formatted in JSON or in plain text
            return (selectedContentType == MediaType.APPLICATION_JSON) ?
                    e : e.getMessage();
        }

        // If current user is autorized to access this resource : we returns the original response
        return body;
    }
}
