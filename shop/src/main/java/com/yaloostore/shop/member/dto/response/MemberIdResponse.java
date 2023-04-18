package com.yaloostore.shop.member.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


/**
 * 회원 Id 를 담아 보낼 때 사용하는 response dto입니다.
 * */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberIdResponse {

    private Long memberId;

}
