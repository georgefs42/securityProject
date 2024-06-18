package com.george.securityproject.controller;

import com.george.securityproject.model.UserApp;
import com.george.securityproject.services.Html;
import com.george.securityproject.services.Masking;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller // Indicates that this class is a Spring MVC controller
@RequestMapping("/register") // Maps HTTP requests to /register to this controller
public class Registration {

    private final static Logger logger = LoggerFactory.getLogger(Registration.class); // Logger for debugging and logging purposes
    private final PasswordEncoder passwordEncoder; // Password encoder for encoding user passwords
    private final UserDetailsService userDetailsService; // Service for managing user details
    private final Map<String, String> userEmails = new HashMap<>(); // Map to store user emails
    private final Html html; // Service for HTML escaping

    @Autowired // Constructor-based dependency injection
    public Registration(Html html, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, Masking masking) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.html = html;
    }

    // Overloaded constructor for additional flexibility
    public Registration(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, Html html) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.html = html;
    }

    @GetMapping // Handles GET requests to /register
    public String registrationForm(Model model){
        logger.debug("Access of registration form"); // Debug log for form access
        model.addAttribute("user", new UserApp()); // Adds a new UserApp object to the model
        return "register"; // Returns the registration form view
    }

    @PostMapping // Handles POST requests to /register
    public String registerUser(@Valid @ModelAttribute("user") UserApp appUser, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("error", "There are errors in the form, please correct them"); // Adds an error message to the model
            logger.debug("Form has validation errors for attempted registration of user: {}", appUser.getName()); // Debug log for form errors
            System.out.println(appUser);
            return "register"; // Returns the registration form view with errors
        }
        logger.debug("Registration for user with email {}", Masking.maskEmail(appUser.getEmail()) + " done"); // Debug log for successful registration
        String encodedPassword = passwordEncoder.encode(appUser.getPassword()); // Encodes the user password
        appUser.setPassword(encodedPassword); // Sets the encoded password
        UserDetails newUser = User.withUsername(appUser.getName())
                .password(appUser.getPassword())
                .roles("USER")
                .build(); // Builds a new UserDetails object
        ((InMemoryUserDetailsManager) userDetailsService).createUser(newUser); // Adds the new user to the user details manager

        userEmails.put(appUser.getName(), html.escapeHtml(appUser.getEmail())); // Stores the user's email with HTML escaping

        model.addAttribute("user", appUser); // Adds the registered user to the model
        model.addAttribute("userName", appUser.getName()); // Adds the user's name to the model

        return "registerSuccessful"; // Returns the registration successful view
    }

    // Getter method for userEmails map
    public Map<String, String> getUserEmails() {
        return userEmails;
    }
}
