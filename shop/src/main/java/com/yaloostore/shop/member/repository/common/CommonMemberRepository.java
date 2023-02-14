package com.yaloostore.shop.member.repository.common;


import com.yaloostore.shop.member.entity.Member;

import java.util.List;
import java.util.Optional;

public interface CommonMemberRepository {
    Optional<Member> findMemberByMemberId(Long memberId);

    boolean existsMemberById(String id);

    boolean existsMemberByNickname(String nickname);

    boolean existsMemberByEmailAddress(String email);

    /**
     * 닉네임으로 해당 회원 조회 메소드
     * @param nickname
     * @return 해당 닉네임에 해당하는 회원 객체 값
     * */
    Optional<Member> findMemberByNickname(String nickname);


    Member save(Member member);

    List<Member> findAll();
}


