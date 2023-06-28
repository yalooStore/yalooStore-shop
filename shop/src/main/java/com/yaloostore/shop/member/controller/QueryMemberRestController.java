package com.yaloostore.shop.member.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.member.dto.response.MemberDuplicateDto;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/checkEmail/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<MemberDuplicateDto> existMemberByEmail(@PathVariable String email){
        MemberDuplicateDto response = new MemberDuplicateDto(queryMemberService.existMemberByEmail(email));

        return ResponseDto.<MemberDuplicateDto>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data(response)
                .build();


    }
    @GetMapping("/checkNickname/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<MemberDuplicateDto> existMemberBy(@PathVariable String nickname){
        MemberDuplicateDto response =new MemberDuplicateDto(queryMemberService.existMemberByNickname(nickname));

        return ResponseDto.<MemberDuplicateDto>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data(response)
                .build();

    }
    @GetMapping("/checkPhone/{phone}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<MemberDuplicateDto> existMemberByPhoneNumber(@PathVariable String phone){
        MemberDuplicateDto response =new MemberDuplicateDto(queryMemberService.existMemberByPhoneNumber(phone));

        return ResponseDto.<MemberDuplicateDto>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data(response)
                .build();

    }
}
