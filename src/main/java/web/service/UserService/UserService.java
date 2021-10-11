package web.service.UserService;

import web.model.Role;
import web.model.User;
import java.util.List;
import java.util.Set;

public interface UserService {

    User getUserByUsername(String userName);

    User getUser(Long id);

    void saveUser(User user, Set<Role> roles);

    void removeUserById(long id);

    List<User> getAllUsers();

    void updateUser(User user);
}
