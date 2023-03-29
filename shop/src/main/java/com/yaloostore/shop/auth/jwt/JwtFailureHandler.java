package com.yaloostore.shop.auth.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;


/**
 * JWT 인증 로직 수행 중 로그인 실패에 사용되는 핸들러입니다.
 * */
public class JwtFailureHandler implements AuthenticationFailureHandler {


    @Value("${yalooStore.front}")
    private String frontUrl;


    /**
     * JWT 인증 로직으로 로그인 시 인증을 실패했을 때를 제어하기 위한 핸들러 기능입니다.
     * */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.sendRedirect(frontUrl+"/members/login");
    }
}
