package com.yaloostore.shop.member.controller;

import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.service.inter.MemberLoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/service/members")
@RequiredArgsConstructor
public class MemberLoginHistoryRestController {

    private final MemberLoginHistoryService loginHistoryService;


    @PostMapping("/add/loginHistory/{loginId}")
    public ResponseDto<MemberLoginHistoryResponse> addLoginHistory(@PathVariable(name = "loginId") String loginId){
        MemberLoginHistoryResponse response = loginHistoryService.addLoginHistory(loginId);

        return ResponseDto.<MemberLoginHistoryResponse>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }


    @GetMapping("/loginHistory/{today}")
    public ResponseDto<List<MemberIdResponse>> getMemberByLoginHistory(@PathVariable(name ="today") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate today){

        List<MemberIdResponse> memberByLoginHistory = loginHistoryService.findMemberByLoginHistory(today);

        return ResponseDto.<List<MemberIdResponse>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(memberByLoginHistory)
                .build();

    }

}
