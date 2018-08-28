package imie.campus.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import imie.campus.core.controllers.AbstractController;
import imie.campus.dao.UserService;
import imie.campus.model.Views;
import imie.campus.model.dto.UserTO;
import imie.campus.model.entities.User;
import imie.campus.model.mappers.UserMapper;
import imie.campus.security.model.Restricted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static imie.campus.controllers.UserController.RESOURCE_PATH;

@RestController
@Restricted("MODERATOR")
@RequestMapping(RESOURCE_PATH)
public class UserController extends AbstractController<User, UserTO, Integer> {
    public static final String RESOURCE_PATH = "/users";

    @Autowired
    public UserController(UserService service,
                          UserMapper mapper) {
        super(service, mapper);
    }

    @Override
    public String getResourcePath() {
        return RESOURCE_PATH;
    }

    @Override
    @JsonView(Views.Public.class)
    public List<UserTO> getAll() {
        return super.getAll();
    }

    /**
     * Disable a user, making it unable to post or to log in to the service.
     * @param id The technical id for the user
     */
    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity disable(@PathVariable Integer id) {
        User user = service.find(id);
        user.setActive(Boolean.FALSE);
        service.update(id, user);

        return ResponseEntity.status(200).build();
    }
}
