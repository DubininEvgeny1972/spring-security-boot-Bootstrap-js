package web.service.RoleService;

import web.model.Role;
import java.util.Set;

public interface RoleService {
    void saveRole(Set<Role> roles);
    Set<Role> getAllRoles();
}

