package com.yaloostore.shop.member.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.member.dto.request.MemberCreateRequest;
import com.yaloostore.shop.member.dto.request.MemberUpdateRequest;
import com.yaloostore.shop.member.dto.response.*;
import com.yaloostore.shop.member.dto.transfer.MemberDto;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRoleRepository;
import com.yaloostore.shop.member.service.inter.MemberService;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author : yeom hwiju
 * 멤버 회원(가입, 삭제, 수정, 조회를 요청하는 컨트롤러)
 * */


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service/members")
public class MemberRestController {

    private final MemberService memberService;
    private final QueryMemberService queryMemberService;

    /**
     * 회원 가입 Post 요청을 처리
     * @param createRequest 회원가입에 필요한 dto를 넘겨받는다.
     * */
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<MemberCreateResponse> signUpMember(@Valid @RequestBody MemberCreateRequest createRequest) throws URISyntaxException {

        MemberCreateResponse response = memberService.createMember(createRequest);
        log.info("dto = {}", response);

        return ResponseDto.<MemberCreateResponse>builder()
                .status(HttpStatus.CREATED)
                .success(true)
                .data(response)
                .build();

    }

    @PutMapping("/edit/{memberId}")
    public ResponseEntity<MemberUpdateResponse> updateMember(@Valid @RequestBody MemberUpdateRequest updateRequest,
                                                             @PathVariable Long memberId){

        MemberUpdateResponse response = memberService.updateMember(memberId,updateRequest);

        return ResponseEntity.ok(response);
    }


    /**
     * 회원 삭제에 사용하는 컨트롤러
     *
     * @param id 회원 탈퇴를 원하는 회원 로그인에 사용하는 회원 아이디값
     * @return 탈퇴한 회원 객체
     * */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto<MemberSoftDeleteResponse> deleteMember(@PathVariable String id){

        MemberSoftDeleteResponse response = queryMemberService.softDeleteLoginId(id);

        return ResponseDto.<MemberSoftDeleteResponse>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data(response)
                .build();
    }



    @PutMapping("/modify/inactive")
    public ResponseDto<List<InactiveMemberResponse>> changeInactiveMembers(@RequestBody List<MemberIdResponse> inactiveMembersList){
        List<InactiveMemberResponse> inactiveMemberResponses = memberService.changeInactiveMembers(inactiveMembersList);

        return ResponseDto.<List<InactiveMemberResponse>>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data(inactiveMemberResponses)
                .build();
    }


    @GetMapping("/token/test")
    public String test(HttpServletRequest request,
                       HttpServletResponse response){
        String token = request.getHeader("Authorization");
        log.info(token);
        return token;
    }


}
