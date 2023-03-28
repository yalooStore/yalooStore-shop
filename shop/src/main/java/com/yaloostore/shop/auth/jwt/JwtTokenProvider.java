package com.yaloostore.shop.auth.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.security.Keys;


/**
 * JWT 토큰 생성, 재발급, 해당 회원 정보 조회등에 사용되는 클래스입니다.
 * */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = Duration.ofHours(1).toMillis();
    private static final long REFRESH_TOKEN_EXPIRE_TIME = Duration.ofDays(7).toMillis();


    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;


    /**
     * JWT 생성을 위해 HMAC-SHA 알고리즘을 사용해 JWT에 서명할 키를 생성해주는 메소드
     *
     * @param jwtSecretKey 생성을 위해 사용하는 키
     * @return 인코딩된 키를 기반으로 HMAC-SHA 알고리즘으로 생성한 키를 반환합니다.
     * */
    private Key getSecurityKey(String jwtSecretKey){
        byte[] keyBytes = jwtSecretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    /**
     * JWT 토큰 발급 기능
     * */
    public String createToken(String loginId, List<String> roles, long tokenExpireTime){
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("roles", roles);
        Date date = new Date();

        /**
         * jwt
         * header / payload / signature
         * header = 알고리즘, 토큰 타입
         * payload = claim 키:값으로 구성 (사용자 속성)
         * */
        return Jwts.builder()
                .setClaims(claims)//발행한 회원 정보
                .setIssuedAt(date)//발행시간
                .setExpiration(new Date(date.getTime() + tokenExpireTime)) //토큰 유효시간 설정
                .signWith(getSecurityKey(jwtSecretKey), SignatureAlgorithm.HS512) //시그니처 설정 알고리즘과 시크릿키
                .compact();

    }

    /**
     * 토큰 재발급에 사용되는 메소드입니다.
     *
     * @param loginId 회원 로그인에 사용되는 로그인 아이디
     * @param roles 회원 권한 목록
     * @return 재발급된 jwt 토큰입니다.
     * */
    public String refreshToken(String loginId, List<String> roles){

        String accessToken = createAccessToken(loginId, roles);

        return accessToken;

    }

    /**
     * 재발급된 토큰을 발급하는 기능
     * */
    public String createRefreshToken(String loginId, List<String> roles){
        return createToken(loginId, roles, REFRESH_TOKEN_EXPIRE_TIME);
    }

    /**
     * access token 발급하는 기능
     * */
    public String createAccessToken(String loginId, List<String> roles){
        return createToken(loginId, roles, ACCESS_TOKEN_EXPIRE_TIME);
    }


    /**
     * jwt payload에 들어있는 회원 로그인 아이디를 반환하는 메소드입니다.
     * @param token JWT 토큰에서 회원정보를 가져오기 위한 파라미터
     * @return 토큰에서 찾은 회원 로그인 아이디
     * */
    public String extractLoginId(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSecurityKey(jwtSecretKey))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    /**
     * jwt payload에 있는 만료시간을 가져오는 메소드입니다.
     * */
    public Date extractExpireTime(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getSecurityKey(jwtSecretKey))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }


    /**
     * 토큰 유효성 검사
     * */
    public boolean isValidToken(String token){
        try {

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSecurityKey(jwtSecretKey))
                    .build()
                    .parseClaimsJws(token);

        }catch (Exception e){
            return false;
        }
        return true;
    }

    /**
     * jwt 토큰으로 유저 정보가 담긴 인증 객체 반환을 위한 메소드
     *
     * @param token jwt토큰을 사용해 해당 회원 아이디로 유저객체를 찾아온다.
     * @return 사용자 인증 객체 (jwt를 사용해서 조회한), id, password, 역할
     * */
    public Authentication getAuthenticationByJwtToken(String token){
        //로그인 아이디를 사용해서 유저 객체를 가져올 수 있게 한다.
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractLoginId(token));

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );

    }




}
