package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService.RoleService;
import web.service.UserService.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/adminpage")
    public String ShowAdminPage(ModelMap model, Principal principal) {
        User userNew = new User();
        userNew.setName("Duba");
        model.addAttribute("userNew", userNew);
        model.addAttribute("users", userService.findAll());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("thisUser", userService.getUserByUsername(principal.getName()));
        return "adminpageAllUsers";
    }

    @GetMapping("/{id}/deleteUser")
    public String deleteUser(@PathVariable("id") long id){
        userService.removeUserById(id);
        return "redirect:/admin/adminpage";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") long id) {
        User editUser = userService.getById(id);
        editUser.setRoles(roleService.getAllRoles());
        model.addAttribute("user", editUser);
        return "edit";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user, @ModelAttribute("thisUser") User thisUser) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   " + user);
        System.out.println("???????????????????????????????   " + thisUser);
        if (user.getRoles().size() != 0) {
            Set<Role> roleForUpdateUser = new HashSet<>();
            for(Role role: user.getRoles()) {
                roleForUpdateUser.add(roleService.findRole(role));
            }
            user.setRoles(roleForUpdateUser);
        } else {
            user.setRoles(userService.getById(user.getId()).getRoles());
        }
        userService.updateUser(user);
        return "redirect:/admin/adminpage";
    }

    @GetMapping("/adduser")
    public String addUser(@ModelAttribute("user") User user, ModelMap model){
        user.setRoles(roleService.getAllRoles());
        model.addAttribute("user", user);
        return "new";
    }

    @PostMapping("/createuser")
    public String createNewUser(@ModelAttribute("userNew") User user, ModelMap model, @ModelAttribute("name") String name, @PathVariable("name") String name1) {
        System.out.println("1111111111111111111111111111    " + name);
        System.out.println("22222222222222222222222222222    " + name1);
        Set<Role> roleForSaveUser = new HashSet<>();
        if (user.getRoles().size() != 0) {
            for(Role role: user.getRoles()) {
                roleForSaveUser.add(roleService.findRole(role));
            }
            user.setRoles(roleForSaveUser);
        } else {
            Role rol = roleService.findRole(new Role("ROLE_USER"));
            roleForSaveUser.add(rol);
            user.setRoles(roleForSaveUser);
        }

        userService.saveUser(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/admin/adminpage";
    }
}
