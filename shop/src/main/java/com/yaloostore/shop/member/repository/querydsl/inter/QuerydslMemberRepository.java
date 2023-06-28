package com.yaloostore.shop.member.repository.querydsl.inter;


import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.entity.Member;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Query dsl을 사용한 MemberRepository 입니다.
 * */
public interface QuerydslMemberRepository {

    Optional<Member> queryFindUndeletedMember(Long memberId);

    Optional<Member> queryFindUndeletedMemberLoginId(String loginId);


    /**
     * 해당(생년월일) 일자를 기준으로 생일 회원 찾아오는 메소드
     *
     * @param searchBirth 검색하려는 달, 일
     * @return n일 후에 생일인 회원들 목록
     * */
    List<Member> queryFindBirthdayMember(String searchBirth);

    /**
     * 월, 일 기준으로 생일 회원 찾아오는 메소드
     *
     * @param searchMonthDay 검색하려는 달, 일
     * @return n일 후에 생일인 회원들 목록
     * */
    List<MemberIdResponse> queryFindMemberByBirthMonthDay(String searchMonthDay);

    /**
     * 회원 닉네임을 기준으로 해당 회원을 찾아오는 메소드입니다.
     * */
    Optional<Member> findMemberByNickname(String nickname);

    /**
     * 회원 휴대전화번호를 기준으로 해당 회원을 찾아오는 메소드입니다.
     * */
    Optional<Member> findMemberByPhoneNumber(String phoneNumber);

    /**
     * 회원 이메일을 기준으로 해당 회원을 찾아오는 메소드입니다.
     * */
    Optional<Member> findMemberByEmail(String email);

    /**
     * 회원 닉네임을 기준으로 해당 회원을 찾아오는 메소드입니다.
     * */
    boolean existMemberByNickname(String nickname);

    /**
     * 회원 휴대전화번호를 기준으로 해당 회원을 찾아오는 메소드입니다.
     * */
    boolean existMemberByPhoneNumber(String phoneNumber);

    /**
     * 회원 이메일을 기준으로 해당 회원을 찾아오는 메소드입니다.
     * */
    boolean existMemberByEmail(String email);

}
