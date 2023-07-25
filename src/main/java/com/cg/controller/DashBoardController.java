package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashBoardController {

    @GetMapping
    public String dashboardPage(){
        return "dashboard/blank-dashboard-page";
    }

    @GetMapping("/appointments")
    public String appointmentPage(){
        return null;
    }

    @GetMapping("/customers")
    public String customersPage(){
        return "dashboard/customer/list";
    }
    @GetMapping("/doctors")
    public String doctorsPage(){
        return "dashboard/doctor/list";
    }
    @GetMapping("/rooms")
    public String roomsPage(){
        return "dashboard/room/list";
    }
    @GetMapping("/specialities")
    public String specialitiesPage(){
        return "dashboard/speciality/list";
    }
    @GetMapping("/users")
    public String usersPage(){
        return null;
    }
}
