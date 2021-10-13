package web.service.RoleService;

import web.Dao.RoleDao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void saveRole(Set<Role> roles) {
        roleDao.saveRole(roles);
    }

    @Override
    public Set<Role> getAllRoles() {
        return  roleDao.getAllRoles();
    }

    @Override
    public Role findRole(Role role) {
        return roleDao.findRole(role);
    }
}
