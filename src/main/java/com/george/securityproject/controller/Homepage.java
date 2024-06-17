package com.george.securityproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Homepage {

    // Handles GET requests to the root URL ("/")
    @GetMapping
    public String welcome() {
        // Returns the name of the view to be rendered, in this case "homepage"
        return "homepage";
    }
}