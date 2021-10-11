package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import web.dao.UserDao.UserDao;
import web.model.Role;
import web.model.User;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserDao dao;
    @Autowired
    public CustomUserDetailsService(UserDao dao) {
        this.dao = dao;
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
        User myUser= dao.getUserByUsername(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+userName);
        }
        List<GrantedAuthority> roleList = getAuthoritiesEntities(myUser.getRoles());
        org.springframework.security.core.userdetails.User usd = new org.springframework.security.core.userdetails.User(myUser.getLogin(), myUser.getPassword(), roleList);
        return usd;
    }
}