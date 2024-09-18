package com.hfn.SpringOAuthApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.hfn.SpringOAuthApp.service.DefaultUserServiceImpl;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DefaultUserServiceImpl customUserDetailsService;
    private final AuthenticationSuccessHandler successHandler;

    public SecurityConfig(DefaultUserServiceImpl customUserDetailsService, AuthenticationSuccessHandler successHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.successHandler = successHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customUserDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/registration", "/oauth2/authorization/google").permitAll() // Allow Google OAuth2 endpoint and registration page without authentication
                                .requestMatchers("/adminScreen").hasAuthority("ADMIN") // Restrict access to admin screen
                                .anyRequest().authenticated() // Require authentication for all other requests
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Custom login page
                                .successHandler(successHandler) // Custom success handler
                                .permitAll() // Allow access to the login page and form submissions without authentication
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // URL for logout requests
                                .logoutSuccessUrl("/login?logout") // Redirect URL after successful logout
                                .invalidateHttpSession(true) // Invalidate the session
                                .clearAuthentication(true) // Clear authentication
                                .deleteCookies("JSESSIONID") // Ensure JSESSIONID cookie is deleted
                                .permitAll() // Allow access to logout functionality without authentication
                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .loginPage("/login") // Use the same login page for OAuth2 login
                                .defaultSuccessUrl("/profile", true) // Redirect URL after successful OAuth2 login
                );

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}

