package com.yaloostore.shop.member.repository.querydsl.inter;


import com.yaloostore.shop.member.entity.Member;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Query dsl을 사용한 MemberRepository 입니다.
 * */
public interface QuerydslMemberRepository {

    Optional<Member> queryFindUndeletedMember(Long memberId);

    Optional<Member> queryFindUndeletedMemberLoginId(String loginId);


    /**
     * 해당 일자를 기준으로 n일 후의 생일인 회원을 찾아오는 메소드
     *
     * @param searchDay 검색하려는 달, 일
     * @return n일 후에 생일인 회원들 목록
     * */
    List<Member> queryFindBirthdayMember(String searchDay);

}
