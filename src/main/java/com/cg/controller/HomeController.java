package com.cg.controller;

import com.cg.model.User;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String login(){
        return "dashboard/user/login";

    }
    @GetMapping("/register")
    public String register(){
        return "dashboard/user/register";
    }

    @GetMapping
    public String homePage(Model model){
        String username = appUtils.getPrincipalUsername();
        User user = userService.getByUsername(username);
        String roleName = user.getRole().getName().name();



        return "homePage/index";
    }

}
