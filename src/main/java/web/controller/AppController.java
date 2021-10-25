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
        System.out.println("Я вывел всех юзеров! :  " + users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/admin/user") //поиск 1 аюмина
    public ResponseEntity<User> getAuthenticationAdmin(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        System.out.println("Я админ, я авторизовался! :  " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/user/user") //поиск 1 юзера
    public ResponseEntity<User> getAuthenticationUser(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        System.out.println("Я юзер, я авторизовался! :  " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> apiGetOneUser(@PathVariable("id") long id) {
        User user = userService.getById(id);
        System.out.println("Я " + user.getName() + " юзер с Id = " +id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/admin/add") //добавление юзера
    public ResponseEntity<User> insert(@RequestBody User user) {
        System.out.println("Создаю юзера " + user);
        Set<Role> rol = new HashSet<>();
        rol.add(roleService.findRole(new Role("ROLE_USER")));
        user.setRoles(rol);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/admin/{id}}") //изменение юзера
    public ResponseEntity<User> update(@RequestBody User user) {
        System.out.println("Редактирую юзера " + user);
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}") //удаление юзера
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        System.out.println("Удаляю юзера с Ид = " + id);
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/admin/roles")
    public ResponseEntity<Iterable<Role>> findAllRoles() {
        System.out.println("Отдаю роли " + roleService.getAllRoles());
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }
}
