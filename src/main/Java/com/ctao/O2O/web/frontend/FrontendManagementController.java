package com.ctao.O2O.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/frontend",method = RequestMethod.GET)
public class FrontendManagementController {
    @RequestMapping("/index")
    public String index(){
        return "/frontend/index";
    }

    @RequestMapping("/shoplist")
    public String shopList(){
        return "/frontend/shoplist";
    }
}
