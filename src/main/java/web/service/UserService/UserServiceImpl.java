package web.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.Repository.UserRepository.UsersRepository;
import web.model.Role;
import web.model.User;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private PasswordEncoder passwordEncoder;
    private UsersRepository usersRepository;
    @Autowired
    public UserServiceImpl(@Lazy UsersRepository usersRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return usersRepository.loadUserByUsername(userName);
    }

    @Override
    public User getUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        return usersRepository.getUserByUsername(username);
    }

    @Override
    public User getById(Long id){
        return usersRepository.getById(id);
    }

    @Override
    public void saveUser(User user, Set<Role> roles){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.saveUser(user, roles);
    }

    @Override
    public void removeUserById(long id){
        usersRepository.removeUserById(id);
    }

    @Override
    public List<User> findAll(){
        return usersRepository.findAll();
    }

    @Override
    public void updateUser(User user) {
        user.setRoles(usersRepository.getById(user.getId()).getRoles());
        if (!user.getPassword().equals(usersRepository.getById(user.getId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        usersRepository.updateUser(user);
    }
}
