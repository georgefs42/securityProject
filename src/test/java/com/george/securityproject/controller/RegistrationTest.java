package com.george.securityproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    // Test case for successful registration
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testSuccessfulRegistration() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "newUser");
        params.add("password", "newPassword");
        params.add("confirmPassword", "newPassword");

        mockMvc.perform(post("/register")
                        .params(params)
                        .with(csrf())) // Includes CSRF token
                .andExpect(status().isOk()) // Expects HTTP 200 OK status
                .andExpect(view().name("register")); // Expects to show "registrationSuccess" view
    }

    // Test case for failed registration due to password mismatch
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testRegistrationFailedPasswordMismatch() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", "newUser");
        params.add("password", "newPassword");
        params.add("confirmPassword", "wrongPassword");

        // Use BindingResult directly to simulate a failed validation
        mockMvc.perform(post("/register")
                        .params(params)
                        .with(csrf())) // Includes CSRF token
                .andExpect(status().isOk()) // Expects HTTP 200 OK status, since we do not expect a 400
                .andExpect(view().name("register")) // Expects to show "registration" view again
                .andExpect(model().attributeExists("error")) // Checks that the error exists in the model
                .andExpect(model().attribute("error", "There are errors in the form, please correct them")); // Checks the error message
    }

}
