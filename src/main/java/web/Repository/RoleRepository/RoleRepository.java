package web.Repository.RoleRepository;

import web.model.Role;
import java.util.Set;

public interface RoleRepository {
    void saveRole(Set<Role> roles);

    Set<Role> getAllRoles();

}
