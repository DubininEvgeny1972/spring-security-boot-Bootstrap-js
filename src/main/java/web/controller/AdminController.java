package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService.RoleService;
import web.service.UserService.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @GetMapping(value = "/adminpage")
    public String ShowAdminPage(ModelMap model) {
        model.addAttribute("users", service.findAll());
        return "shouallwuser";
    }

    @GetMapping("/{id}/deleteUser")
    public String deleteUser(@PathVariable("id") long id){
        service.removeUserById(id);
        return "redirect:/admin/adminpage";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") long id) {
        User editUser = service.getById(id);
        editUser.setRoles(roleService.getAllRoles());
        model.addAttribute("user", editUser);
        return "edit";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user) {
        if (user.getRoles().size() != 0) {
            Set<Role> roleForUpdateUser = new HashSet<>();
            for(Role role: user.getRoles()) {
                roleForUpdateUser.add(roleService.findRole(role));
            }
            user.setRoles(roleForUpdateUser);
        } else {
            user.setRoles(service.getById(user.getId()).getRoles());
        }
        service.updateUser(user);
        return "redirect:/admin/adminpage";
    }

    @GetMapping("/adduser")
    public String addUser(@ModelAttribute("user") User user, ModelMap model){
        user.setRoles(roleService.getAllRoles());
        model.addAttribute("user", user);
        return "new";
    }

    @PostMapping("/createuser")
    public String createNewUser(@ModelAttribute("user") User user, ModelMap model) {
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

        service.saveUser(user);
        model.addAttribute("users", service.findAll());
        return "redirect:/admin/adminpage";
    }
}
