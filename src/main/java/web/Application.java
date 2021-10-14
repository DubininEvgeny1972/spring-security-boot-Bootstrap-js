package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.model.Role;
import web.model.User;
import web.service.RoleService.RoleService;
import web.service.UserService.UserService;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {
    static UserService userService;
    static RoleService roleService;

    @Autowired
    public Application(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        User user1 = new User();
        user1.setName("Bob");
        user1.setLastName("Dillan");
        user1.setAge((byte) 48);
        user1.setLogin("111@mail.ru");
        user1.setPassword("111");
        User user2 = new User();
        user2.setName("Susana");
        user2.setLastName("Marpl");
        user2.setAge((byte) 35);
        user2.setLogin("222@mail.ru");
        user2.setPassword("222");

        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        Set<Role> setStartRoles = new HashSet<>();
        setStartRoles.add(role1);
        setStartRoles.add(role2);
        roleService.saveRole(setStartRoles);
        user1.setRoles(roleService.getAllRoles());
        userService.saveUser(user1);
        user2.setRoles(roleService.getAllRoles().stream().limit(1).collect(Collectors.toSet()));
        userService.saveUser(user2);
    }
}
