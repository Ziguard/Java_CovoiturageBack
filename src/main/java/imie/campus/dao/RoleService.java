package imie.campus.dao;

import imie.campus.core.services.AbstractEntityService;
import imie.campus.model.entities.Role;
import imie.campus.model.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends AbstractEntityService<Role, Integer> {
    public static final String ROLE_KEY = "USER" ;

    @Autowired
    public RoleService(RoleRepository repository) {
        super(repository);
    }

    public Role findByKey(String key) {
        return ((RoleRepository) repository).findByKey(key);
    }
}
