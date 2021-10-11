package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.model.Role;
import web.model.User;
import web.service.RoleService.RoleService;
import web.service.UserService.UserService;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StartProject {

    private final UserService service;
    private final RoleService roleService;

    @Autowired
    public StartProject(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @PostConstruct
    public void startProject() {
        User user1 = new User();
        user1.setName("Bob");
        user1.setLastName("Dillan");
        user1.setAge((byte) 48);
        user1.setLogin("111");
        user1.setPassword("111");
        User user2 = new User();
        user2.setName("Susana");
        user2.setLastName("Marpl");
        user2.setAge((byte) 35);
        user2.setLogin("222");
        user2.setPassword("222");

        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        Set<Role> setStartRoles = new HashSet<>();
        setStartRoles.add(role1);
        setStartRoles.add(role2);
        roleService.saveRole(setStartRoles);
        service.saveUser(user1, setStartRoles.stream().limit(1).collect(Collectors.toSet()));
        service.saveUser(user2, setStartRoles);
    }
}
