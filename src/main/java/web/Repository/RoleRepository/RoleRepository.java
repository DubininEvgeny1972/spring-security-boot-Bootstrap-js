package web.Repository.RoleRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.model.Role;
import java.util.Set;

public interface RoleRepository {
    void saveRole(Set<Role> roles);

    Set<Role> getAllRoles();

}
