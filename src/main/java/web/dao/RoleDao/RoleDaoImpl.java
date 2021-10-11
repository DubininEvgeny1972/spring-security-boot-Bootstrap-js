package web.dao.RoleDao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImpl implements RoleDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveRole(Set<Role> roles) {
        for (Role role: roles) {
            em.persist(role);
        }
    }
    @Override
    public Set<Role> getAllRoles() {
        return (Set<Role>) em.createQuery("from Role ").getResultList().stream().collect(Collectors.toSet());
    }
}
