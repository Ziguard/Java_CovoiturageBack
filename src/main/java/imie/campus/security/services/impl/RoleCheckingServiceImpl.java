package imie.campus.security.services.impl;

import imie.campus.security.model.RoleTemplate;
import imie.campus.security.services.RoleCheckingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleCheckingServiceImpl implements RoleCheckingService {
    @Override
    @SuppressWarnings("unchecked")
    public <A> Collection<RoleTemplate<A>> computeHierarchy(RoleTemplate<A> baseRole) {
        RoleTemplate<A> cursor = baseRole;
        final Collection<RoleTemplate<A>> hierarchy = new ArrayList<>();

        // While the cursor has a parent
        do {
            // We add it to the hierarchy
            hierarchy.add(cursor);
            cursor = cursor.getParent();
        } while (cursor != null);

        return hierarchy;
    }

    @Override
    public boolean matches(Set<? extends RoleTemplate<?>> userRoles,
                           String authorizedRole) {
        Set<String> computedHierarchy = userRoles.stream()
                .map(this::computeHierarchy)
                .flatMap(Collection::stream)
                .map(RoleTemplate::getKey)
                .collect(Collectors.toSet());

        return computedHierarchy.contains(authorizedRole);
    }
}
