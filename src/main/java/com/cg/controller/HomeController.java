package com.cg.controller;

import com.cg.model.Customer;
import com.cg.model.User;
import com.cg.model.enums.ETime;
import com.cg.service.customer.ICustomerService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/login")
    public String login(){
        return "dashboard/user/login";

    }
    @GetMapping("/register")
    public String register(){
        return "dashboard/user/register";
    }

//    @GetMapping
//    public String homePage(Model model){
//        String username = appUtils.getPrincipalUsername();
//        User user = userService.getByUsername(username);
//        String roleName = user.getRole().getName().name();
//
//
//
//        return "homepage/index";
//    }

    @GetMapping
    public ModelAndView accessHomepage(Principal user) {
        ModelAndView model = new ModelAndView("homepage/index");
        if (user == null) {
            return model;
        }
        String username = user.getName();
        User user1 = userService.getByUsername(username);
        String roleName = user1.getRole().getName().name();
        model.addObject("user", user);
        model.addObject("username", username);
        model.addObject("roleName", roleName);
        return model;
    }

    @GetMapping("/profile")
    public ModelAndView profilePage(Principal user) {
        ModelAndView model = new ModelAndView("homepage/profile");
        ModelAndView modelChoose = new ModelAndView("homepage/profile-choose");
        String username = user.getName();
        User user1 = userService.getByUsername(username);
        Long userId = user1.getId();

        Customer customer = customerService.findCustomerByUserId(userId);
        if (customer == null || customer.isDeleted()) {
            model.addObject("user", user);
            return model;
        }
        modelChoose.addObject("user", user);
        modelChoose.addObject("customer", customer);
        return modelChoose;
    }

    @GetMapping("/profile-create")
    public ModelAndView profilePageCreate(Principal user) {
        String username = user.getName();
        User user1 = userService.getByUsername(username);
        Long userId = user1.getId();
        ModelAndView modelAndView = new ModelAndView("homepage/profile-create");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userId", userId);
        return modelAndView;
    }

    @GetMapping("/profile-update")
    public ModelAndView profilePageUpdate(Principal user) {
        String username = user.getName();
        User user1 = userService.getByUsername(username);
        Long userId = user1.getId();
        Customer customer = customerService.findCustomerByUserId(userId);
        ModelAndView modelAndView = new ModelAndView("homepage/profile-update");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userId", userId);
        modelAndView.addObject("customer", customer);
        return modelAndView;
    }

    @GetMapping("/choose-appointment")
    public String chooseAppointment(Model model){

        String username = appUtils.getPrincipalUsername();
        User user = userService.getByUsername(username);
        Long userId = user.getId();
        Customer customer = customerService.findCustomerByUserId(userId);
        Long customerId = customer.getId();

        model.addAttribute("customerId",customerId);

        Map<String, String> times = new HashMap<>();
        for (ETime eTime: ETime.values()
        ) {
            times.put(eTime.name(),eTime.getValue());
        }

        model.addAttribute("times",times);

        return "homepage/choose-appointment";
    }

    @GetMapping("/appointment-confirm")
    public String cart(Model model){

        return "homepage/appointment-confirm";
    }
}
