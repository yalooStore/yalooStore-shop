package com.yaloostore.shop.member.dto.response;


import com.yaloostore.shop.member.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class InactiveMemberResponse {

    private Long memberId;
    private boolean isInactiveMember;
    private String emailAddress;

    public static InactiveMemberResponse fromEntity(Member member){
        return InactiveMemberResponse.builder()
                .memberId(member.getMemberId())
                .isInactiveMember(true)
                .emailAddress(member.getEmailAddress())
                .build();
    }


}
