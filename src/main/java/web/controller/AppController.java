package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService.RoleService;
import web.service.UserService.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class AppController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public AppController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin/users") //  поиск всех юзеров
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/user") //поиск 1 админа
    public ResponseEntity<User> getAuthenticationAdmin(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/user/user") //поиск 1 юзера
    public ResponseEntity<User> getAuthenticationUser(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> apiGetOneUser(@PathVariable("id") long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/admin/add") //добавление юзера
    public ResponseEntity<User> insert(@RequestBody User user) {
        Set<Role> roleForUpdateUser = new HashSet<>();
        user.getRoles().forEach((element) -> roleForUpdateUser.add(roleService.findRole(element)));
        user.setRoles(roleForUpdateUser);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<User> apiUpdateUser(@PathVariable("id") long id, @RequestBody User user) {
        if (user.getRoles().size() != 0) {
            Set<Role> roleForUpdateUser = new HashSet<>();
            user.getRoles().forEach((element) -> roleForUpdateUser.add(roleService.findRole(element)));
            user.setRoles(roleForUpdateUser);
        } else {
            user.setRoles(userService.getById(user.getId()).getRoles());
        }
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}") //удаление юзера
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/admin/roles")
    public ResponseEntity<Iterable<Role>> findAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}
