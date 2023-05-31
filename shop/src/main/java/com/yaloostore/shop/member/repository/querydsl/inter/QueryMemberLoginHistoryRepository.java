package com.yaloostore.shop.member.repository.querydsl.inter;

import com.yaloostore.shop.member.dto.response.MemberIdResponse;

import java.time.LocalDate;
import java.util.List;

public interface QueryMemberLoginHistoryRepository {
    /**
     * 1년간 로그인 기록이 없는 회원을 찾아오는 메소드
     *
     * @param start 오늘을 기준으로 -1년 합니다.
     * @param end 오늘
     * @return 1년간 로그인하지 않은 회원 목록 dto
     * */
    List<MemberIdResponse> queryFindMemberBySleeper(LocalDate start);


}
