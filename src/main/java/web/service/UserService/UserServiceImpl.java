package web.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao.UserDao;
import web.model.Role;
import web.model.User;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDaoHiber, PasswordEncoder passwordEncoder) {
        this.userDao = userDaoHiber;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return userDao.getUserByUsername(username);
    }

    @Transactional
    @Override
    public User getUserByUsername(String userName){
        return userDao.getUserByUsername(userName);
    }

    @Transactional
    @Override
    public User getUser(Long id){
        return userDao.getUser(id);
    }

    @Transactional
    @Override
    public void saveUser(User user, Set<Role> roles){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user, roles);
    }

    @Transactional
    @Override
    public void removeUserById(long id){
        userDao.removeUserById(id);
    }

    @Transactional
    @Override
    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        user.setRoles(userDao.getUser(user.getId()).getRoles());
        if (!user.getPassword().equals(userDao.getUser(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }
}
