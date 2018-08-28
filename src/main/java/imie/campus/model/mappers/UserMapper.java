package imie.campus.model.mappers;

import imie.campus.core.mappers.AbstractEntityMapper;
import imie.campus.dao.RoleService;
import imie.campus.model.dto.UserTO;
import imie.campus.model.entities.Role;
import imie.campus.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashSet;

import static imie.campus.core.mappers.converters.Converters.collectionFunction;
import static imie.campus.core.mappers.converters.ReverseConverters.reverseCollectionFunction;
import static imie.campus.core.mappers.converters.StandardConverters.dateFromString;
import static imie.campus.core.mappers.converters.StandardConverters.dateToString;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

@Service
@Scope("singleton")
public class UserMapper extends AbstractEntityMapper<User, UserTO> {

    private final RoleService roleService;

    @Autowired
    public UserMapper(RoleService roleService) {
        super(User.class, UserTO.class);
        this.roleService = roleService;
    }

    @Override
    public void configure() {
        defaultTypeMap().addMappings(mapper -> {
            mapper.using(collectionFunction(Role::getKey, HashSet::new))
                    .map(User::getRoles, UserTO::setRoles);
            mapper.using(dateToString(ISO_DATE_TIME)).map(User::getCreationDate, UserTO::setCreationDate);
        });
        reverseTypeMap().addMappings(mapper -> {
            mapper.using(reverseCollectionFunction(roleService::findByKey, HashSet::new))
                    .map(UserTO::getRoles, User::setRoles);
            mapper.using(dateFromString(ISO_DATE_TIME)).map(UserTO::getCreationDate, User::setCreationDate);
        });
    }
}
