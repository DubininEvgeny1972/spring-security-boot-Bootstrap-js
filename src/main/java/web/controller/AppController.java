package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService.RoleService;
import web.service.UserService.UserService;
import java.security.Principal;
import java.util.List;

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
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   " + users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping(value = "/user/user") //поиск 1 юзера
    public ResponseEntity<User> getAuthentication(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@    " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/{id}}") //поиск юзера по ИД
    public ResponseEntity<User> getOne(@PathVariable Long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users/add") //добавление юзера
    public ResponseEntity<User> insert(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/edit") //изменение юзера
    public ResponseEntity<User> update(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/remove/{id}") //удаление юзера
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping(value = "/roles")
//    public ResponseEntity<Iterable<Role>> findAllRoles() {
//        return ResponseEntity.ok(appService.findAllRoles());
//    }


}
