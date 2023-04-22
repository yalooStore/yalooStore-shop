package com.yaloostore.shop.member.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/api/service/members")
@RestController
@RequiredArgsConstructor
public class QueryMemberRestController {


    private final QueryMemberService queryMemberService;


    /**
     * 오늘을 기준으로 n일 후 생일인 회원을 불러오는 컨트롤러 입니다.
     *
     * @param laterDays
     * @return n일 후가 생일인 회원 dto 객체
     * */
    @GetMapping(value ="/birthday", params ={"laterDays"})
    public ResponseDto<List<MemberIdResponse>> getMemberByBirthMonthDay(@RequestParam(value = "laterDays", defaultValue = "0") int laterDays){
        List<MemberIdResponse> responses = queryMemberService.findMemberIdByLateDay(laterDays);

        return ResponseDto.<List<MemberIdResponse>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(responses)
                .build();




    }
}
