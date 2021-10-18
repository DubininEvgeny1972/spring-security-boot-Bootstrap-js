package web.service.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;
import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String userName);

    User getUserByUsername(String userName);

    User getById(Long id);

    void saveUser(User user);

    void removeUserById(long id);

    List<User> findAll();

    void updateUser(User user);
}
