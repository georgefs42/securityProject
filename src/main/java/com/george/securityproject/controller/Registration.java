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

@Controller
@RequestMapping("/register")
public class Registration {

    private final static Logger logger = LoggerFactory.getLogger(Registration.class);
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final Map<String, String> userEmails = new HashMap<>();
    private final Html html;

    @Autowired
    public Registration(Html html, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, Masking masking) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.html = html;
    }

    public Registration(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, Html html) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.html = html;
    }

    @GetMapping
    public String registrationForm(Model model){
        logger.debug("Access of registration form");
        model.addAttribute("user", new UserApp());
        return "register";
    }

    @PostMapping
    public String registerUser(@Valid @ModelAttribute("user") UserApp appUser,BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("error", "There are errors in the form, please correct them");
            logger.debug("Form has validation errors for attempted registration of user: {}", appUser.getName());
            System.out.println(appUser);
            return "register";
        }
        logger.debug("Registration for user with email {}", Masking.maskEmail(appUser.getEmail()) + " done");
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        UserDetails newUser = User.withUsername(appUser.getName())
                .password(appUser.getPassword())
                .roles("USER")
                .build();
        ((InMemoryUserDetailsManager) userDetailsService).createUser(newUser);

        userEmails.put(appUser.getName(), html.escapeHtml(appUser.getEmail()));

        model.addAttribute("user", appUser);
        model.addAttribute("userName", appUser.getName());

        return "registerSuccessful";

    }

    public Map<String, String> getUserEmails() {
        return userEmails;
    }
}