package com.example.alumninetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().authorizeHttpRequests(authorizeConfig -> {
                    authorizeConfig.requestMatchers("/swagger-ui/index.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    authorizeConfig.anyRequest().authenticated();
                }
        );
        http.oauth2ResourceServer().jwt();
        return http.build();
    }
}