package com.george.securityproject.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@EnableWebSecurity // Enables Spring Security’s web security support and provides the Spring MVC integration
@Configuration // Indicates that this class contains one or more @Bean methods
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class); // Logger for debugging and logging purposes

    @Bean // Defines a bean for the SecurityFilterChain
    public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
        logger.debug("Security Chain with HttpSecurity started"); // Debug log indicating the start of security chain configuration
        http
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/register", "/admin", "/userManager").hasRole("ADMIN") // Only users with ADMIN role can access these endpoints
                                .anyRequest().authenticated()) // All other requests require authentication
                .formLogin(formLogin ->{
                    formLogin
                            .defaultSuccessUrl("/", true) // Redirect to home page upon successful login
                            .successHandler((request, response, authentication) -> {
                                String username = request.getParameter("username");
                                logger.warn("Login succeeded with for user : {}", username); // Warn log for successful login
                                response.sendRedirect("/");
                            })
                            .failureUrl("/login?error=true") // Redirect to login page with error on failure
                            .failureHandler((request, response, exception) -> {
                                String username = request.getParameter("username");
                                logger.warn("Login failed with attempted User name: {}", username); // Warn log for failed login
                                response.sendRedirect("/login?error=true");
                            })
                            .permitAll(); // Allow all users to access login page
                })
                .logout(logout ->{
                    logout
                            .logoutUrl("/performLogout") // URL to trigger logout
                            .logoutSuccessUrl("/login") // Redirect to login page upon successful logout
                            .permitAll(); // Allow all users to access logout URL
                })
                .csrf(csrf ->{
                    logger.debug("CSRF protection configuration deployed"); // Debug log for CSRF protection configuration
                    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // CSRF protection with HTTP-only false for CSRF token
                });
        return http.build(); // Build the HttpSecurity object
    }

    @Bean // Defines a bean for UserDetailsService
    public UserDetailsService userDetailsService() {
        logger.debug("Default users created"); // Debug log indicating creation of default users
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        // Creating a default user with USER role
        var user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        // Creating a default admin with ADMIN role
        var admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        userDetailsManager.createUser(user); // Adding user to the user details manager
        userDetailsManager.createUser(admin); // Adding admin to the user details manager
        return userDetailsManager; // Returning the user details manager
    }

    @Bean // Defines a bean for PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Using BCrypt for password encoding
    }
}
