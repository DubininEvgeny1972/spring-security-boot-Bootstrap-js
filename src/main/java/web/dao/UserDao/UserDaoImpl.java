package web.dao.UserDao;

import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;
import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public User getUserByUsername(String userName) {
        return em.createQuery("select userByUsername from User userByUsername where userByUsername.login = :usName", User.class)
                .setParameter("usName", userName)
                .getSingleResult();
    }

    @Override
    public User getUser(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void saveUser(User user, Set<Role> roles) {
        em.persist(user);
        Set<Role> roleForSaveUser = new HashSet<>();
        for(Role role: roles) {
            roleForSaveUser.add(em.createQuery("SELECT r FROM Role r WHERE r.name=:name", Role.class)
                    .setParameter("name", role.toString())
                    .getSingleResult());
        }
        user.setRoles(roleForSaveUser);
    }

    @Override
    public void removeUserById(long id) {
        em.remove(em.find(User.class, new Long(id)));
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("from User ").getResultList();
    }
}
