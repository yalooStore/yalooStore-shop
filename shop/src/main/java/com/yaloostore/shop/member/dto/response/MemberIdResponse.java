package com.yaloostore.shop.member.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.yaloostore.shop.member.entity.Member;
import lombok.*;


/**
 * 회원 Id 를 담아 보낼 때 사용하는 response dto입니다.
 * */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberIdResponse {

    private Long memberId;


    public static MemberIdResponse fromEntity(Member member){
        return MemberIdResponse.builder()
                .memberId(member.getMemberId())
                .build();
    }

}
