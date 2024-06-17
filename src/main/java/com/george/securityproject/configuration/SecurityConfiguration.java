package com.george.securityproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    // Configures the security filter chain for HTTP security
    @Bean
    public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
        http
                // Authorize HTTP requests based on roles
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/register", "/admin", "/userManager").hasRole("ADMIN") // Allow only ADMIN role to access specified paths
                                .anyRequest().authenticated()) // All other requests need to be authenticated
                // Configure form login
                .formLogin(formLogin ->
                        formLogin
                                .defaultSuccessUrl("/", true) // Redirect to home on successful login
                                .failureUrl("/login?error=true") // Redirect to login page with error on failure
                                .permitAll() // Allow everyone to access the login page
                )
                // Configure logout
                .logout(logout ->
                        logout
                                .logoutUrl("/performLogout") // URL to trigger logout
                                .logoutSuccessUrl("/login") // Redirect to login page on successful logout
                                .permitAll() // Allow everyone to access the logout URL
                )
                // Configure CSRF protection with cookies
                .csrf(csrf ->
                        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );
        return http.build(); // Build the security filter chain
    }

    // Configures the in-memory user details service with predefined users
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        // Create a user with USER role
        var user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password")) // Encode the password
                .roles("USER")
                .build();

        // Create an admin with ADMIN role
        var admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password")) // Encode the password
                .roles("ADMIN")
                .build();

        // Add users to the in-memory user details manager
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(admin);
        return userDetailsManager; // Return the in-memory user details manager
    }

    // Configures the password encoder to use BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Return BCrypt password encoder
    }
}