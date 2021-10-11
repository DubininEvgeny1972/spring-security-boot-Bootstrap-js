package web.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.RoleDao.RoleDao;
import web.model.Role;
import java.util.Set;

@Service
@Transactional
public class RoleServiseImpl implements RoleService {
    private RoleDao roleDao;
    @Autowired
    public RoleServiseImpl(RoleDao roleDao) {
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


}
