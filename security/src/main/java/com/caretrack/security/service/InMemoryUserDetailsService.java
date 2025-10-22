package com.caretrack.security.service;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class InMemoryUserDetailsService {

    @Bean
    public UserDetailsService userDetailsService() {
        UserBuilder builder = User.withDefaultPasswordEncoder();
        List<UserDetails> users = List.of(
                builder.username("admin").password("password").roles("ADMIN").build(),
                builder.username("user").password("password").roles("USER").build()
        );
        return new InMemoryUserDetailsManager(users);
    }
}
