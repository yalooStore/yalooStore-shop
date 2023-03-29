package com.yaloostore.shop.auth.service.impl;

import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.auth.dto.response.MemberResponseDto;
import com.yaloostore.shop.member.controller.MemberRestController;
import com.yaloostore.shop.member.controller.QueryMemberLoginRestController;
import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    private final RestTemplate restTemplate;

    private final QueryMemberService queryMemberService;

    @Value("${yalooStore.shop.url}")
    private String shopUrl;

    /**
     * spring security를 사용해서 로그인 진행 시 로그인 아이디를 사용해서 사용자를 찾는 메소드입니다.
     *
     * @param loginId 로그인을 하려는 사용자의 로그인 아이디
     * @return 해당 로그인아이디를 사용해서 찾은 사용자 UserDetails 사용자 정보
     * */
//    @Override
//    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
//
//
//        URI uri = UriComponentsBuilder.fromUriString(shopUrl+"/api/service/members/login" + loginId).encode().build().toUri();
//
//        ResponseEntity<ResponseDto<MemberResponseDto>> response =
//                restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), new ParameterizedTypeReference<>() {
//                });
//
//
//        MemberResponseDto memberResponseDto = response.getBody().getData();
//
//
//        if (Objects.isNull(memberResponseDto)){
//            throw new UsernameNotFoundException(loginId);
//        }
//
//        User user = new User(memberResponseDto.getLoginId(),
//                memberResponseDto.getPassword(),
//                memberResponseDto.getRoles()
//                        .stream().map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList()));
//
//        return user;
//    }

    /**
     * Rest api 통신이 아닌 해당 서비스 로직을 불러와 사용하는 서비스 로직
     * */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

         MemberLoginResponse memberResponseDto = queryMemberService.findMemberByLoginId(loginId);


         User user = new User(memberResponseDto.getLoginId(),
                memberResponseDto.getPassword(),
                memberResponseDto.getRoles()
                        .stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));

        return user;
    }


}
