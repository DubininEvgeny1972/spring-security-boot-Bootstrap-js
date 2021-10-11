package web;

import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.Set;

public class StartApplication {

//    static UserService userService;
//    static RoleService roleService;
//    @Autowired
//    public Application(UserService service, RoleService roleService) {
//        this.userService = service;
//        this.roleService = roleService;
//    }

    public static void main(String[] args) {

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
//        roleService.saveRole(setStartRoles);
//        userService.saveUser(user1, roleService.getAllRoles().stream().limit(1).collect(Collectors.toSet()));
//        userService.saveUser(user2, roleService.getAllRoles());
    }
}
