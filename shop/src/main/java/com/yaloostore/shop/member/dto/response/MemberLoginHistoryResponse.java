package com.yaloostore.shop.member.dto.response;


import com.yaloostore.shop.member.entity.MemberLoginHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * 회원 로그인 이력이 1년이 넘은 회원을 돌려줄 때사용하는 response dto입니다.
 * */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginHistoryResponse {

    private Long historyId;
    private Long memberId;
    private String loginId;
    private LocalDate loginTime;

    public static MemberLoginHistoryResponse fromEntity(MemberLoginHistory entity){
        return new MemberLoginHistoryResponse(
                entity.getId(),
                entity.getMember().getMemberId(),
                entity.getMember().getId(),
                entity.getLoginTime()
        );
    }

}
