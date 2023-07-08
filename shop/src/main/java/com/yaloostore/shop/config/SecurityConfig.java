package com.yaloostore.shop.config;

import com.yaloostore.shop.auth.JwtAuthenticationFilter;
import com.yaloostore.shop.auth.JwtAuthenticationProvider;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;


@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${yalooStore.auth.url}")
    private String authServerUrl;

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests().anyRequest().permitAll();

        http.formLogin().disable();
        http.logout().disable();
        http.csrf().disable();
        http.httpBasic().disable();
        http.cors().disable();

        http.addFilterBefore(jwtAuthenticationFilter(http), UsernamePasswordAuthenticationFilter.class);
        //스프링 시큐리티에서 생성주는 세션이 아닌 jwt 와 같은 토큰 방식을 사용할 때 이 설정을 진행해주어야 한다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter(HttpSecurity http) throws Exception {
        return new JwtAuthenticationFilter(authenticationManager(http));
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(jwtAuthenticationProvider(null))
                .build();
    }

    @Bean
    public AuthenticationProvider jwtAuthenticationProvider(RestTemplate restTemplate) {
        return new JwtAuthenticationProvider(restTemplate, authServerUrl);
    }

}
