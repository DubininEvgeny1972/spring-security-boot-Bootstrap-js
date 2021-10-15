package web.Dao.UserDao;

import org.springframework.security.core.userdetails.UserDetails;
import web.model.Role;
import web.model.User;
import java.util.List;
import java.util.Set;

public interface UserDao {

    User getById(Long id);

    User getUserByUsername(String userName);

    UserDetails loadUserByUsername(String userName);

    void removeUserById(long id);

    List<User> findAll();

    void saveUser(User user);

    void updateUser(User user);
}
