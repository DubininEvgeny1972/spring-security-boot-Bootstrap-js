package web.Dao.UserDao;

import web.model.User;
import java.util.List;

public interface UserDao {

    User getById(Long id);

    User getUserByUsername(String userName);

    void removeUserById(long id);

    List<User> findAll();

    void saveUser(User user);

    void updateUser(User user);
}
