package com.yaloostore.shop.auth.service.impl;

import com.yaloostore.shop.auth.jwt.JwtTokenProvider;
import com.yaloostore.shop.auth.service.inter.AuthorizationService;
import com.yaloostore.shop.member.dto.response.AuthorizationMetaResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService {

    private final JwtTokenProvider jwtTokenProvider;


    /***
     *
     * @param removePrefixBearer type Bearer을 제거한 순수 token 값
     * @return 인가처리 후 정보 응답객체
     * */
    @Override
    public AuthorizationMetaResponse authorization(String removePrefixBearer) {

        //jwt 혹은 oauth를 사용할 경우 Bearer<type>에 해당해 토큰처리 진행
        jwtTokenProvider.isValidToken(removePrefixBearer);

        Authentication authentication = jwtTokenProvider.getAuthenticationByJwtToken(removePrefixBearer);

        List<String> authority = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new AuthorizationMetaResponse(authentication.getName(), authority);
    }
}
