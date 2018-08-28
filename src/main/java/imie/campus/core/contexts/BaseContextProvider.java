package imie.campus.core.contexts;

import imie.campus.dao.UserService;
import imie.campus.security.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;

@Service
public class BaseContextProvider implements ContextProvider<AuthRequestContext> {

    private final UserService userService;
    private final AuthenticationService authService;

    @Autowired
    public BaseContextProvider(UserService userService,
                               AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public AuthRequestContext provide(ServletRequest request) {
        return AuthRequestContext.from(request,
                userService.findByUsername(
                        authService.checkAuthentication(request).getUsername()));
    }
}
