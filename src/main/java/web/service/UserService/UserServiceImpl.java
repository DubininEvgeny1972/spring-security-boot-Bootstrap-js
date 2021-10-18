package web.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Dao.UserDao.UserDao;
import web.model.Role;
import web.model.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    @Autowired
    public UserServiceImpl(@Lazy UserDao userDao, @Lazy PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }
    private List<GrantedAuthority> getAuthoritiesEntities(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        });
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User myUser= getUserByUsername(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
        }
        List<GrantedAuthority> roleList = getAuthoritiesEntities(myUser.getRoles());
        org.springframework.security.core.userdetails.User usd = new org.springframework.security.core.userdetails.User(myUser.getLogin(), myUser.getPassword(), roleList);
        return usd;
    }

    @Override
    public User getUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return userDao.getUserByUsername(username);
    }

    @Override
    public User getById(Long id){
        return userDao.getById(id);
    }

    @Override
    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveUser(user);
    }

    @Override
    public void removeUserById(long id){
        userDao.removeUserById(id);
    }

    @Override
    public List<User> findAll(){
        return userDao.findAll();
    }

    @Override
    public void updateUser(User user) {
        if (!user.getPassword().equals(userDao.getById(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }
}
