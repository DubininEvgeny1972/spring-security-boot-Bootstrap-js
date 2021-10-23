package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/admin/page")
    public String getAdminPage() {
        return "index";
    }

    @GetMapping(value = "/user/page")
    public String getUserPage() {
        return "thisuser";
    }

}