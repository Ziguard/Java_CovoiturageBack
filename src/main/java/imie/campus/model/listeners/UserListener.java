package imie.campus.model.listeners;

import imie.campus.core.contexts.RequestContext;
import imie.campus.core.listeners.BaseEntityListener;
import imie.campus.dao.RoleService;
import imie.campus.model.entities.Role;
import imie.campus.model.entities.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class UserListener extends BaseEntityListener<User> {

    private final RoleService roleService;

    @Autowired
    public UserListener(RoleService roleService) {
        this.roleService = roleService;
    }


    @Override
    public void beforeCreate(User newer, RequestContext context) {
        Role user = roleService.findByKey(RoleService.ROLE_KEY);
        newer.setRoles(Collections.singleton(user));
        newer.setCreationDate(LocalDateTime.now());

        String salt = BCrypt.gensalt();
        newer.setSalt(salt);
        newer.setPassword(BCrypt.hashpw(newer.getPassword(), salt));
    }

}
