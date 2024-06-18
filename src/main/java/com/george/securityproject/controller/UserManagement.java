package com.george.securityproject.controller;

import com.george.securityproject.services.Html;
import com.george.securityproject.services.Masking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userManager")
public class UserManagement {

    private final UserDetailsService userDetailsService;
    private final Registration registration;
    private final Html html;
    private final Masking masking;

    @Autowired
    public UserManagement(Html html, UserDetailsService userDetailsService, Registration registration, Masking masking) {
        this.html = html;
        this.userDetailsService = userDetailsService;
        this.registration = registration;
        this.masking = masking;
    }

    @GetMapping
    public String userManagementPage(Model model, @RequestParam(value = "errorUsername", required = false) String errorUsername) {
        InMemoryUserDetailsManager userDetailsManager = (InMemoryUserDetailsManager) userDetailsService;
        List<UserDetails> users = new ArrayList<>();
        Map<String, String> userEmails = registration.getUserEmails();

        try {
            Field field = InMemoryUserDetailsManager.class.getDeclaredField("users");
            field.setAccessible(true);
            Map<String, UserDetails> usersMap = (Map<String, UserDetails>) field.get(userDetailsManager);
            users.addAll(usersMap.values());
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }

        List<String> maskedEmails = new ArrayList<>();
        for (UserDetails user : users) {
            String username = user.getUsername();
            String email = userEmails.get(username);
            String maskedEmail = masking.maskEmail(email); // Mask the email using Masking service
            maskedEmails.add(maskedEmail);
        }

        model.addAttribute("users", users);
        model.addAttribute("userEmails", maskedEmails);
        if (errorUsername != null) {
            model.addAttribute("errorUsername", errorUsername);
        }

        return "userManger";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("username") String username, @RequestParam("email") String email, Model model) {
        InMemoryUserDetailsManager userDetailsManager = (InMemoryUserDetailsManager) userDetailsService;
        Map<String, String> userEmails = registration.getUserEmails();
        String registeredEmail = userEmails.get(username);

        if (!registeredEmail.equals(email)) {
            model.addAttribute("errorUsername", username);
            return "redirect:/userManger?errorUsername=" + username;
        }

        userDetailsManager.deleteUser(username);
        return "deletedUserSuccessful";
    }

    @GetMapping("/deletedUserSuccessful")
    public String deletedUserSuccessful(){
        return "deletedUserSuccessful";
    }
}
