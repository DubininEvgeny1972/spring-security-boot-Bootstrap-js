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
        StringBuilder roles = new StringBuilder();
        for (Role role: userService.getUserByUsername(principal.getName()).getRoles()){
            roles.append(role.getName()).append(" ");
        }
        model.addAttribute("rolesUser", roles.toString());
        model.addAttribute("userNew", new User());
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

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user) {
        if (user.getRoles().size() != 0) {
            Set<Role> roleForUpdateUser = new HashSet<>();
            user.getRoles().forEach((element) -> roleForUpdateUser.add(roleService.findRole(element)));
            user.setRoles(roleForUpdateUser);
        } else {
            user.setRoles(userService.getById(user.getId()).getRoles());
        }
        userService.updateUser(user);
        return "redirect:/admin/adminpage";
    }

    @PostMapping("/createuser")
    public String createNewUser(@ModelAttribute("user") User user, ModelMap model) {
        Set<Role> roleForSaveUser = new HashSet<>();
        user.getRoles().forEach((element) -> roleForSaveUser.add(roleService.findRole(element)));
        user.setRoles(roleForSaveUser);
        userService.saveUser(user);
        model.addAttribute("users", userService.findAll());
        return "redirect:/admin/adminpage";
    }
}
