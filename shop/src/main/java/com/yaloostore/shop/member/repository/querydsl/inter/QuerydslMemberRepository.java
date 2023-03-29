package com.yaloostore.shop.member.repository.querydsl.inter;


import com.yaloostore.shop.member.entity.Member;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Query dsl을 사용한 MemberRepository 입니다.
 * */
public interface QuerydslMemberRepository {

    Optional<Member> queryFindUndeletedMember(Long memberId);

    Optional<Member> queryFindUndeletedMemberLoginId(String loginId);

}
