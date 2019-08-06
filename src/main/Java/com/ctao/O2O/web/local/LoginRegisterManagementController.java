package com.ctao.O2O.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/local")
public class LoginRegisterManagementController {
    @RequestMapping("/login")
    public String login(){
        return "/local/login";
    }
    @RequestMapping("/register")
    public  String register(){
        return "/local/register";
    }
}
