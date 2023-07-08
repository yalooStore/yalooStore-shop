package com.yaloostore.shop.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * UsernamePasswordAuthenticationToken과 같은 기능을 담당하는 Jwt 관련 Authentication객체를 생성할 때 사용하는 클래스입니다.
 * */
@RequiredArgsConstructor
public class JwtAuthenticationToken implements Authentication {


    private boolean isAuthenticated;
    private final String token;
    private final String loginId;
    private final List<String> roles;


    /**
     * 초반 필터 작업에선 인증되지 않은 사용자를 다음 작업으로 위임할 때 생성해서 넘겨줍니다.
     * 이때 해당 메소드를 사용해서 인증되지 않은 사용자(authenticationToken)를 생성해 넘겨줍니다.
     * */
    public static JwtAuthenticationToken unAuthenticated(String token){
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                token,
                null,
                null
        );
        //비인증 객체
        jwtAuthenticationToken.setAuthenticated(false);
        return jwtAuthenticationToken;
    }

    /**
     * Provide에서 해당 사용자의 인증 작업을 완료 했다면 해당 인증 객체를 넘겨줍니다.
     * */
    public static JwtAuthenticationToken Authenticated(String token, String loginId, List<String> roles){
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
                token,
                loginId,
                roles

        );
        //비인증 객체
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }


    /**
     * UserDetails 생성에 사용합니다.
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.isNull(roles) ? null: roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * token 값을 credentials 넘겨준다.
     * */
    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return new User(loginId, null, getAuthorities());

    }

    @Override
    public Object getPrincipal() {
        return loginId;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return loginId;
    }
}
