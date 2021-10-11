package web.service.RoleService;

import web.Repository.RoleRepository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveRole(Set<Role> roles) {
        roleRepository.saveRole(roles);
    }

    @Override
    public Set<Role> getAllRoles() {
        return  roleRepository.getAllRoles();
    }


}
