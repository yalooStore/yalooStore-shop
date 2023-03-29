package com.yaloostore.shop.auth.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.auth.exeption.InvalidTokenException;
import com.yaloostore.shop.auth.service.inter.AuthorizationService;
import com.yaloostore.shop.member.dto.response.AuthorizationMetaResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 인가처리를 담당하는 컨트롤러입니다.
 * */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service/authorization")
public class AuthorizationRestController {

    //토큰 앞에 붙는 값이 Bearer
    private static final String BEARER_PREFIX = "Bearer ";
    private final AuthorizationService authorizationService;


    /**
     *[Get - /api/service/authorization]
     *
     * @param authorization request header: Authorization: <type=Bearer><credentials=pure token>
     * @return 인가된 회원의 정보(=Meta)가 있는 객체 dto 객체
     * */
    @GetMapping(headers = "Authorization")
    public ResponseDto<AuthorizationMetaResponse> authorization(@RequestHeader(name = "Authorization") String authorization){

        AuthorizationMetaResponse metaResponse = authorizationService.authorization(removePrefixBearer(authorization));

        return ResponseDto.<AuthorizationMetaResponse>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(metaResponse)
                .build();
    }

    private String removePrefixBearer(String tokenWithPrefix) {

        if (!tokenWithPrefix.startsWith(BEARER_PREFIX)){
            throw new InvalidTokenException();
        }

        //7번째까지 Bearer ------여기가 토큰----- 실제 토큰값을 보냄.
        return tokenWithPrefix.substring(7, tokenWithPrefix.length());
    }


}
