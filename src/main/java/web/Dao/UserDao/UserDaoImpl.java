package web.Dao.UserDao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User getUserByUsername(String userName) {
        return em.createQuery("select userByUsername from User userByUsername where userByUsername.login = :usName", User.class)
                .setParameter("usName", userName)
                .getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public User getById(Long id) {
        return em.find(User.class, id);
    }


    @Override
    public void saveUser(User user) {
        em.persist(user);
    }

    @Override
    public void removeUserById(long id) {
        em.remove(em.find(User.class, new Long(id)));
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("from User ").getResultList();
    }

}
