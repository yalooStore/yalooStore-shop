package com.yaloostore.shop.member.controller;

import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.service.inter.MemberLoginHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
