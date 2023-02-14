package com.yaloostore.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(request -> request
//                .anyRequest("/dd").permitAll()
//                        .requestMatchers("/api/service/members/sign-up").permitAll());
                //.csrf(AbstractHttpConfigurer::disable);

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.csrf().disable();
        http.httpBasic().disable();
        http.cors().disable();
        return http.build();
    }
}
