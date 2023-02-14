package com.yaloostore.shop.member.dto.response;

import com.yaloostore.shop.member.entity.Member;
import lombok.*;


@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class MemberUpdateResponse {

    private Long memberId;
    private String name;
    private String nickname;
    private String id;
    private String membershipGrade;


    /**
     * Member entity -> MemberUpdateResponse DTO(Request)로 변환하는 메소드
     * @param member entity
     * @return member entity가 아닌 MemberUpdateResponse DTO 객체로 반환
     * */
    public static MemberUpdateResponse fromEntity(Member member){
        return new MemberUpdateResponse(
                member.getMemberId(),
                member.getName(),
                member.getNickname(),
                member.getId(),
                member.getMembership().getGrade().getGrade()
        );
    }

}