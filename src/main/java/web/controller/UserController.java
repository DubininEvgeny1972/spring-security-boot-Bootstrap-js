package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.service.UserService.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/thisuser")
    public Object getAuthentication(@CurrentSecurityContext(expression = "authentication")
                                                Authentication authentication,
                                                Model model) {
        model.addAttribute("showUser", userService.getUserByUsername(authentication.getName()));
        return "thisuser";
    }
}
