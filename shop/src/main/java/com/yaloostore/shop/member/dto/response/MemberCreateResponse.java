package com.yaloostore.shop.member.dto.response;

import com.yaloostore.shop.member.common.Grade;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.role.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateResponse {
    private Long memberId;
    private String name;
    private String nickname;
    private String id;
    private String grade;
    private String role;

    /**
     * @param
     * @return 결과로 반환된 엔티티의 일부 데이터를 사용해서 Response DTO로 반환
     *         회원 가입 시 회원의 등급은 WHITE 등급으로 고정
     *         이 DTO의 경우엔 회원 가입 기능에서만 사용된다.
     *
     * */
    public static MemberCreateResponse fromEntity(Member member, Role role){
        return new MemberCreateResponse(
                member.getMemberId(),
                member.getName(),
                member.getNickname(),
                member.getId(),
                Grade.WHITE.getName(),
                role.getRoleType().getRoleName()
        );
    }

}
