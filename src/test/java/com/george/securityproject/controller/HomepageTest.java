package com.george.securityproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Indicates that this is a Spring Boot test
@AutoConfigureMockMvc // Automatically configures MockMvc for testing
public class HomepageTest {

    @Autowired
    private MockMvc mockMvc; // Injects an instance of MockMvc to run MVC tests

    // Test case for unauthenticated access to the homepage
    @Test
    public void testHomepageUnauthenticated() throws Exception {
        mockMvc.perform(get("/")) // Makes an HTTP GET request to "/"
                .andExpect(status().isUnauthorized()); // Expects HTTP 401 Unauthorized
    }

    // Test case for authenticated access to the homepage with the role "USER"
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testHomepageAuthenticated() throws Exception {
        mockMvc.perform(get("/")) // Makes an HTTP GET request to "/"
                .andExpect(status().isOk()); // Expects HTTP status 200 (OK)
    }
}
