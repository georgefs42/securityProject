package com.george.securityproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class to verify access to the homepage ("/").
 * The class contains two test cases:
 * 1. A test case to verify that unauthenticated users cannot access the homepage and instead receive a 401 Unauthorized status.
 * 2. A test case to verify that authenticated users with the role "USER" can access the homepage and receive a 200 OK status.
 */
@SpringBootTest // Indicates that this is a Spring Boot test
@AutoConfigureMockMvc // Automatically configures MockMvc for the test
public class HomepageTest {

    @Autowired
    private MockMvc mockMvc; // Injects an instance of MockMvc to perform MVC tests and verify responses without starting a full web server

    /**
     * Test case for unauthenticated access to the homepage.
     * Verifies that unauthenticated users receive a 401 Unauthorized status when trying to access the homepage.
     */
    @Test
    public void testHomepageUnauthenticated() throws Exception {
        mockMvc.perform(get("/")) // Perform an HTTP GET request to "/"
                .andExpect(status().isUnauthorized()); // Expects HTTP 401 Unauthorized
    }

    /**
     * Test case for authenticated access to the homepage with the role "USER".
     * Verifies that authenticated users with the role "USER" receive a 200 OK status when trying to access the homepage.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"}) // Simulates a logged-in user with the role "USER"
    public void testHomepageAuthenticated() throws Exception {
        mockMvc.perform(get("/")) // Perform an HTTP GET request to "/"
                .andExpect(status().isOk()); // Expects HTTP status to be 200 (OK)
    }

}
