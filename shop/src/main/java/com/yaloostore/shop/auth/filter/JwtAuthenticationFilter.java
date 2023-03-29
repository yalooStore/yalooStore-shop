package com.yaloostore.shop.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaloostore.shop.auth.exeption.InvalidLoginRequestException;
import com.yaloostore.shop.auth.jwt.JwtTokenProvider;
import com.yaloostore.shop.member.dto.request.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.XMLFormatter;
import java.util.stream.Collectors;

import static com.yaloostore.shop.auth.utils.AuthUtil.*;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String AUTHORIZATION_HEADER ="Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String UUID_HEADER = "UUID_HEADER";
    private static final String X_EXPIRE_HEADER = "X-Expire";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * front 서버에서 로그인일 시도할 때 사용자가 입력한 로그인아이디와 패스워드를 기반으로
     * UsernamePasswordAuthenticationToken을 발급한 후 authenticationManager에게 인가를 위임합니다.
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {


        ObjectMapper mapper = new ObjectMapper();
        LoginRequest loginRequest;

        try {
            loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);

        } catch (IOException e) {
            e.printStackTrace();
            throw new InvalidLoginRequestException();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getLoginId(),
                loginRequest.getPassword()
        );


        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 인증 성공 후처리를 위한 메소드입니다.
     * JWT 토큰 발급, redis 저장, http header Authorization 필드에 해당 accessToken 반환
     * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String accessToken = getAccessToken(auth);
        String refreshToken = getRefreshToken(auth);
        long expiredTime = jwtTokenProvider.extractExpireTime(accessToken).getTime();

        String uuid = UUID.randomUUID().toString();

        redisTemplate.opsForHash().put(uuid, REFRESH_TOKEN.getValue(), refreshToken);
        redisTemplate.opsForHash().put(uuid, ACCESS_TOKEN.getValue(), accessToken);
        redisTemplate.opsForHash().put(uuid, USER_ID.getValue(), auth.getName());
        redisTemplate.opsForHash().put(uuid, PRINCIPALS.getValue(), auth.getAuthorities().toString());


        response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + accessToken);
        response.addHeader(UUID_HEADER, uuid);
        response.addHeader(X_EXPIRE_HEADER,String.valueOf(expiredTime));

    }

    private String getRefreshToken(Authentication auth) {
        return jwtTokenProvider.createAccessToken(auth.getName(),
                auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    private String getAccessToken(Authentication auth) {
        return jwtTokenProvider.createAccessToken(auth.getName(),
                auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        getFailureHandler().onAuthenticationFailure(request, response, failed);

    }
}
