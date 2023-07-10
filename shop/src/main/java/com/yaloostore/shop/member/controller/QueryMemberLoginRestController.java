package com.yaloostore.shop.member.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 회원 로그인 처리를 위해서 인증, 인가 로직과 통신하는 rest api controller입니다.
 * */
@RestController
@RequestMapping("/api/service/members")
@RequiredArgsConstructor
public class QueryMemberLoginRestController {


    private final QueryMemberService queryMemberService;

    /**
     * 회원의 로그인 아이디를 기준으로 회원 정보와 권한 정보를 조회하는 메소드입니다.
     * */
    @GetMapping("/login/{loginId}")
    public ResponseDto<MemberLoginResponse> login(@PathVariable String loginId){
        MemberLoginResponse response = queryMemberService.findMemberByLoginId(loginId);

        return ResponseDto.<MemberLoginResponse>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(response)
                .build();

    }
}
