package com.george.securityproject.DTO;

public class UserDTO {

    private String name; // Field to store user's name
    private String email; // Field to store user's email address
    private String password; // Field to store user's password

    // Constructors

    /**
     * Default constructor.
     */
    public UserDTO() {
    }

    /**
     * Constructor to initialize UserDTO with name, email, and password.
     *
     * @param name     The name of the user
     * @param email    The email address of the user
     * @param password The password of the user
     */
    public UserDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters

    /**
     * Retrieves the name of the user.
     *
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name The name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return The email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    // Override toString() method

    /**
     * Returns a string representation of the UserDTO object.
     *
     * @return A string representation of the UserDTO object
     */
    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
