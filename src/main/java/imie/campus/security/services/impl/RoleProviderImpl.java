package imie.campus.security.services.impl;

import imie.campus.dao.RoleService;
import imie.campus.security.model.RoleTemplate;
import imie.campus.security.services.RoleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleProviderImpl implements RoleProvider {
    private final RoleService roleService;

    private final List<RoleTemplate<?>> roles;

    @Autowired
    public RoleProviderImpl(RoleService roleService) {
        this.roleService = roleService;
        this.roles = new ArrayList<>();
        this.update();
    }

    @Override
    public List<RoleTemplate<?>> getRoles() {
        return roles;
    }

    @Override
    public void update() {
        roles.clear();
        roles.addAll(roleService.findAll());
    }
}
