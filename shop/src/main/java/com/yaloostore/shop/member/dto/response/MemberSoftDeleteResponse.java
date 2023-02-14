package com.yaloostore.shop.member.dto.response;


import com.yaloostore.shop.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 회원 id 논리삭제의 경우 response 해줄 dto 객체
 * */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberSoftDeleteResponse {

    private Long memberId;
    private String name;
    private boolean isSoftDelete;
    private LocalDateTime memberSoftDeletedAt;


    /**
     * Entity -> 삭제된 회원 DTO로 변환해주는 메소드
     *
     * @param member 삭제한 회원 entity
     * @return 삭제한 회원 dto
     * */
    public static MemberSoftDeleteResponse fromEntity(Member member){
            return new MemberSoftDeleteResponse(
                    member.getMemberId(),
                    member.getName(),
                    member.isSoftDelete(),
                    member.getMemberSoftDeletedAt()
            );

    }

}
