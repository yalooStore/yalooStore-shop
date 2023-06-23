package com.yaloostore.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests().anyRequest().permitAll();

        http.formLogin().disable();
        http.logout().disable();
        http.csrf().disable();
        http.httpBasic().disable();
        http.cors().disable();

        //스프링 시큐리티에서 생성주는 세션이 아닌 jwt 와 같은 토큰 방식을 사용할 때 이 설정을 진행해주어야 한다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

}
