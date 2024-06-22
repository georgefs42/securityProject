package com.george.securityproject.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for UserManagementController.
 * This class contains integration tests that verify the behavior of user management functionality.
 * The tests use MockMvc to simulate HTTP requests and check the responses.
 */
@WebMvcTest(UserManagement.class) // Indicates that this is a Spring MVC test for UserManagementController only
class UserManagementTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc object used to simulate HTTP requests to the controller

    @MockBean
    private InMemoryUserDetailsManager userDetailsManager; // Mocked instance of InMemoryUserDetailsManager

    @MockBean
    private Registration registrationController; // Mocked instance of RegistrationController

    /**
     * Preparations before each test case.
     * Here, the behavior of registrationController and userDetailsManager is mocked to simulate
     * certain scenarios related to user management.
     */
    @BeforeEach
    void setUp() throws Exception {
        // Mock the behavior of registrationController
        // Create a mocked Map that holds user email addresses to simulate registrationController
        Map<String, String> mockUserEmails = new HashMap<>();
        mockUserEmails.put("testuser", "testuser@example.com");
        // Mock the behavior of registrationController to return mockUserEmails when getUserEmails is called
        Mockito.when(registrationController.getUserEmails()).thenReturn(mockUserEmails);

        // Mock the behavior of userDetailsManager (create a UserDetails for the test)
        UserDetails user = User
                .withUsername("testuser")
                .password("password")
                .roles("USER")
                .build();

        // Reflective access to the "users" field in InMemoryUserDetailsManager to set mock data
        Field field = InMemoryUserDetailsManager.class.getDeclaredField("users");
        field.setAccessible(true); // Make the field accessible to be able to modify it, even if it is private
        // Create a new HashMap that holds user details to simulate userDetailsManager
        Map<String, UserDetails> usersMap = new HashMap<>();
        usersMap.put("testuser", user); // Add a user "testuser" and user details to usersMap
        field.set(userDetailsManager, usersMap); // Set the value of the "users" field in userDetailsManager to usersMap
    }

    /**
     * Test case for authenticated access to the user management page.
     * The test simulates a logged-in admin user and verifies that the page returns
     * HTTP 200 OK and that the model attributes "users" and "userEmails" exist.
     */
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getUserManagementPageAuthenticated() throws Exception {
        mockMvc.perform(get("/userManager"))
                .andExpect(status().isOk()) // Verify that HTTP status is 200 OK
                .andExpect(view().name("userManager")) // Verify that the view is "userManager"
                .andExpect(model().attributeExists("users")) // Verify that "users" attribute exists in the model
                .andExpect(model().attributeExists("userEmails")); // Verify that "userEmails" attribute exists in the model
    }

    /**
     * Test case for unauthenticated access to the user management page.
     * The test verifies that the page returns HTTP 401 Unauthorized when a user
     * attempts to access the page without authentication.
     */
    @Test
    void getUserManagementPageUnauthenticated() throws Exception {
        mockMvc.perform(get("/userManager"))
                .andExpect(status().isUnauthorized()); // Verify that HTTP status is 401 Unauthorized for unauthenticated access
    }

    /**
     * Test case for successful deletion of a user.
     * The test simulates a POST request to "/userManager/delete" with correct parameters to
     * delete a user and verifies that HTTP 200 OK is returned and that the correct view is displayed.
     */
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUserSuccess() throws Exception {

        mockMvc.perform(post("/userManager/delete")
                        .param("username", "testuser")
                        .param("email", "testuser@example.com")
                        .with(csrf())) // Include CSRF token
                .andExpect(status().isOk()) // Verify that HTTP status is 200 OK
                .andExpect(view().name("deleteUserSuccessful")); // Verify that the view is "deleteUserSuccessful"
    }

    /**
     * Test case for unsuccessful deletion of a user.
     * The test simulates a POST request to "/userManager/delete" with incorrect parameters to
     * delete a user and verifies that HTTP 3xx redirection is returned and that the correct redirect URL is used.
     */
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void deleteUserFailure() throws Exception {
        mockMvc.perform(post("/userManager/delete")
                        .param("username", "testuser")
                        .param("email", "wrongemail@example.com")
                        .with(csrf())) // Include CSRF token
                .andExpect(status().is3xxRedirection()) // Verify that HTTP status is a 3xx redirection
                .andExpect(redirectedUrl("/userManager?errorUsername=testuser")); // Verify that the redirected URL is correct
    }
}
