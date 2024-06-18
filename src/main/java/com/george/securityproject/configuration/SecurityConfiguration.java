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

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Bean
    public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
        logger.debug("Security Chain with HttpSecurity started");
        http
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/register", "/admin", "/userManager").hasRole("ADMIN")
                                .anyRequest().authenticated())
                .formLogin(formLogin ->{
                    formLogin
                            .defaultSuccessUrl("/", true)
                            .successHandler((request, response, authentication) -> {
                                String username = request.getParameter("username");
                                logger.warn("Login succeeded with for user : {}", username);
                                response.sendRedirect("/");
                            })
                            .failureUrl("/login?error=true")
                            .failureHandler((request,response,exception)->{
                                String username = request.getParameter("username");
                                logger.warn("Login failed with attempted User name: {}", username);
                                response.sendRedirect("/login?error=true");
                            })
                            .permitAll();
                })
                .logout(logout ->{
                    logout
                            .logoutUrl("/performLogout")
                            .logoutSuccessUrl("/login")
                            .permitAll();
                })
                .csrf(csrf ->{
                    logger.debug("CSRF protection configuration deployed");
                    csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                });
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        logger.debug("Default users created");
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        var user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();
        var admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();
        userDetailsManager.createUser(user);
        userDetailsManager.createUser(admin);
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}