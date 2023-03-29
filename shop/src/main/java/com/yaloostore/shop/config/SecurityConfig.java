package com.yaloostore.shop.config;

import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import com.yaloostore.shop.auth.filter.JwtAuthenticationFilter;
import com.yaloostore.shop.auth.jwt.JwtFailureHandler;
import com.yaloostore.shop.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;


    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests().anyRequest().permitAll();
        //기본제공되는 로그인, 로그아웃 사용하지 않음
        http.formLogin().disable();
        http.logout().disable();
        //http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        http.csrf().disable();
        http.httpBasic().disable();
        http.cors().disable();

        //스프링 시큐리티에서 생성주는 세션이 아닌 jwt 와 같은 토큰 방식을 사용할 때 이 설정을 진행해주어야 한다.
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {


        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(null), tokenProvider, redisTemplate);

        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        return jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new JwtFailureHandler();
    }
}
