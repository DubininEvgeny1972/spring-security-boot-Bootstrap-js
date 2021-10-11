package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService service;
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/thisuser")
    public String getPrincipal(@CurrentSecurityContext(expression = "authentication.principal")
                               Principal principal,
                               Model model) {
        System.out.println(principal.getName());
        User tmp = service.getUserByUsername(principal.getName());
        model.addAttribute("showUser", tmp);
        return "thisuser";
    }
}
