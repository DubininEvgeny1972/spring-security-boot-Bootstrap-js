package web.dao.UserDao;

import web.model.Role;
import web.model.User;
import java.util.List;
import java.util.Set;

public interface UserDao {

    User getUser(Long id);

    User getUserByUsername(String userName);

    void removeUserById(long id);

    List<User> getAllUsers();

    void saveUser(User user, Set<Role> roles);

    void updateUser(User user);
}
