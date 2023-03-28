package com.yaloostore.shop.auth.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.auth.dto.request.LogoutRequest;
import com.yaloostore.shop.auth.exeption.InvalidAuthorizationHeaderException;
import com.yaloostore.shop.auth.jwt.JwtTokenProvider;
import com.yaloostore.shop.auth.service.inter.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 토큰 재발급, 로그아웃을 위한 인증관련 rest 컨트롤러 클래스
 * 인증관련 로직 처리 진 (자신이 해당 사용자가 맞는지를 확인하는 작업을 여기서 진행 권한 관련해서는 인가에서 진행)
 * */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service/auth")
public class AuthenticationRestController {


    private final JwtTokenProvider tokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AuthenticationService authenticationService;

    private static final String UUID_HEADER = "UUID_HEADER";
    private static final String X_EXPIRE_HEADER ="X-Expire";


    /**
     * 로그아웃에 사용될 컨트롤러 메소드로 프론트 서버에서 해당 요청을 받아 redis 정보를 삭제합니다.
     *
     * post - body 사용 ok (상태 변경이 있다면 post 사용)
     * get - param만 사용 ok (body 사용 불가)
     *
     * @param logoutRequest front 서버에서 logout 요청에 대한 정보를 담은 dto 객체입니다.
     * @return 응답 결과를 담은 공통 dto 객체입니다.
     * */
    @PostMapping("/logout")
    public ResponseDto<Void> logout(
            @RequestBody LogoutRequest logoutRequest,
            @RequestHeader(name = "Authorization") String accessToken

    ){

        String uuid = logoutRequest.getKey();

        if(isValidHeader(accessToken, uuid)){
            throw new InvalidAuthorizationHeaderException();
        }

        if (isValidKey(uuid)){
            authenticationService.logout(uuid);

            return ResponseDto.<Void>builder()
                    .success(true)
                    .status(HttpStatus.OK)
                    .build();
        }

        return ResponseDto.<Void>builder()
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .errorMessages(List.of("이미 로그아웃 한 사용자입니다."))
                .build();

    }


    /**
     * 로그아웃, 토큰 재발급 처리에 앞서서 로그인한 회원이 가지고 있는 식별키가 Redis에 유요한지 판단하기 위한 메소드입니다.
     *
     * @param uuid 로그인한 회원이 가지고 있는 유일한 식별키 입니다.
     * @return 유효한지에 대한 결과값입니다.
     * */
    private boolean isValidKey(String uuid) {
        boolean result = redisTemplate.opsForHash().keys(uuid).isEmpty();

        return !result;
    }

    /**
     * 로그아웃, 토큰 재발급 요청에 대해서 유효한 request header(요청 헤더) 정보인지를 판단하는 메소드입니다.
     *
     * request header - 패치될 리소스, 클라이언트 자체에 대한 정보를 포함하고 있는 헤더를 지칭
     *
     * @param accessToken JWT 토큰입니다.
     * @param uuid 로그인한 회원이 가지고 있는 유일한 식별키 입니다.
     * @return 유효성 검사에 대한 결과값 입니다.
     * */
    private boolean isValidHeader(String accessToken, String uuid) {

        //회원 uuid, accessToken이 없을 경우 유효하지 않음
        // accessToken이 Bearer 로 시작하지 않으면 유효하지 않은 사용자
        // accessToken 값에서 추출한 순수한 token값만을 사용해서 해당 토큰이 유효하지 않은 사용자

        return Objects.isNull(accessToken) || Objects.isNull(uuid) || !accessToken.startsWith("Bearer ") || tokenProvider.isValidToken(accessToken.substring(7, accessToken.length()));

    }


}
