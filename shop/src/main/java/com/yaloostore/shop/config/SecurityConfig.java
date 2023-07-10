package com.yaloostore.shop.config;

import com.yaloostore.shop.auth.JwtAuthenticationFilter;
import com.yaloostore.shop.auth.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Value("${yalooStore.auth.url}")
    private String authServerUrl;
    @Bean
    public JwtAuthenticationProvider jwtTokenAuthenticationProvider(
            RestTemplate restTemplate
    ) {

        return new JwtAuthenticationProvider(restTemplate, authServerUrl);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(jwtTokenAuthenticationProvider(null))
                .build();
    }

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        new JwtAuthenticationFilter(authenticationManager(http)),
                        UsernamePasswordAuthenticationFilter.class
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}