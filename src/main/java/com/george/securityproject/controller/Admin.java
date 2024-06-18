package com.george.securityproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // Indicates that this class is a Spring MVC controller
@RequestMapping("/admin") // Maps this controller to handle requests with "/admin" prefix
public class Admin {

    // Handler method for HTTP GET requests to "/admin"
    @GetMapping
    public String adminPage(){

        return "admin"; // Returns the view name "admin" (admin.html or admin.jsp)
    }

}
