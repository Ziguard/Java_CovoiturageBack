package imie.campus.model.listeners;

import imie.campus.core.contexts.RequestContext;
import imie.campus.core.listeners.BaseEntityListener;
import imie.campus.model.entities.Role;
import imie.campus.security.services.RoleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleListener extends BaseEntityListener<Role> {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RoleProvider roleProvider;

    @Autowired
    public RoleListener(RoleProvider roleProvider) {
        this.roleProvider = roleProvider;
    }

    @Override
    public void beforeUpdate(Role actual, Role updated, RequestContext context) {
        logger.info("Refreshing roles from data source for Security layer.");
        roleProvider.update();
    }

}
