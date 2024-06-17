package com.george.securityproject.controller;

import com.george.securityproject.model.UserApp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class Registration {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final Map<String, String> userEmails = new HashMap<>();

    // Constructor injection for PasswordEncoder and UserDetailsService
    @Autowired
    public Registration(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    // Handles GET requests to "/register", returns the registration form view
    @GetMapping
    public String registrationForm(Model model){
        model.addAttribute("user", new UserApp()); // Add a new UserApp object to the model
        return "register"; // Return the "register" view
    }

    // Handles POST requests to "/register", processes the registration form
    @PostMapping
    public String registerUser(@Valid @ModelAttribute("user") UserApp appUser, BindingResult bindingResult, Model model){
        // Check if there are any validation errors in the form
        if(bindingResult.hasErrors()){
            model.addAttribute("error", "There are errors in the form, please correct them");
            System.out.println(appUser); // Debugging: Print the user details to the console
            return "register"; // Return the "register" view to correct errors
        }

        // Encode the user's password
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        // Create a new UserDetails object with the encoded password and USER role
        UserDetails newUser = User.withUsername(appUser.getName())
                .password(appUser.getPassword())
                .roles("USER")
                .build();

        // Add the new user to the in-memory user details manager
        ((InMemoryUserDetailsManager) userDetailsService).createUser(newUser);

        // Store the user's email in the userEmails map
        userEmails.put(appUser.getName(), appUser.getEmail());

        // Add attributes to the model to pass to the view
        model.addAttribute("user", appUser);
        model.addAttribute("userName", appUser.getName());

        // Return the "regSuccessful" view on successful registration
        return "registerSuccessful";
    }

    // Getter for the userEmails map
    public Map<String, String> getUserEmails() {
        return userEmails;
    }
}