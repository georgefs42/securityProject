package com.george.securityproject.model;

import jakarta.validation.constraints.*;

public class UserApp {

    @NotEmpty(message = "Name is required") // Validation: Name must not be empty
    @Size(min = 2, message = "Minimum of 2 characters for account name") // Validation: Name must be at least 2 characters long
    private String name;

    @NotEmpty(message = "Email is required") // Validation: Email must not be empty
    @Email(message = "Please enter a valid e-mail address") // Validation: Email must be a valid email format
    private String email;

    @NotEmpty(message = "Password is required") // Validation: Password must not be empty
    @Size(min = 2, message = "Minimum of 2 characters for password") // Validation: Password must be at least 2 characters long
    private String password;

    // Getters and Setters for name, email, and password fields

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Method to return a string representation of the UserApp object
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
