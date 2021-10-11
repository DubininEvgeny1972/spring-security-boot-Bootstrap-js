package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService.RoleService;
import web.service.UserService.UserService;

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
        model.addAttribute("users", service.getAllUsers());
        return "shouallwuser";
    }

    @GetMapping("/{id}/deleteUser")
    public String deleteUser(@PathVariable("id") long id){
        service.removeUserById(id);
        return "redirect:/admin/adminpage";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap model, @PathVariable("id") long id) {
        model.addAttribute("user", service.getUser(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user) {
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
        service.saveUser(user, user.getRoles());
        model.addAttribute("users", service.getAllUsers());
        return "redirect:/admin/adminpage";
    }
}
