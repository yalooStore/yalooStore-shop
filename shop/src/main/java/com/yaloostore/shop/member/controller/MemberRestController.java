package com.yaloostore.shop.member.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.member.dto.request.MemberCreateRequest;
import com.yaloostore.shop.member.dto.request.MemberUpdateRequest;
import com.yaloostore.shop.member.dto.response.MemberCreateResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;
import com.yaloostore.shop.member.dto.response.MemberUpdateResponse;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRoleRepository;
import com.yaloostore.shop.member.service.inter.MemberService;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
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
    public ResponseEntity<MemberCreateResponse> signUpMember(@Valid @RequestBody MemberCreateRequest createRequest) throws URISyntaxException {

        MemberCreateResponse response = memberService.createMember(createRequest);
        log.info("dto = {}", response);
        return ResponseEntity.created(new URI(response.getId().toString())).body(response);

    }

    @PutMapping("/{memberId}")
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

    @GetMapping("/test")
    public String test(){
        return "8081 port test : yalooStore-shop";
    }



}
