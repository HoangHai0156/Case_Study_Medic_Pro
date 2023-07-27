package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/login")
    public String login(){
        return "dashboard/user/login";

    }
    @GetMapping("/register")
    public String register(){
        return "dashboard/user/register";
    }

}
