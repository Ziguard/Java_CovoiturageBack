package imie.campus.controllers;

import imie.campus.dao.UserService;
import imie.campus.model.dto.UserTO;
import imie.campus.model.mappers.UserMapper;
import imie.campus.security.controllers.AbstractLoginController;
import imie.campus.security.model.Credentials;
import imie.campus.security.model.LoginResponse;
import imie.campus.security.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends AbstractLoginController {

    private final UserService service;
    private final UserMapper mapper;

    @Autowired
    public LoginController(AuthenticationService authenticationService,
                           UserService service, UserMapper mapper) {
        super(authenticationService);
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    @SuppressWarnings("unchecked")
    public LoginResponse<UserTO> login(@RequestBody Credentials creds) {
        LoginResponse<UserTO> response =
                (LoginResponse<UserTO>) super.performLogin(creds);
        UserTO user = mapper.map(service.findByUsername(response.getUsername()));
        response.setPayload(user);

        return response;
    }
}
