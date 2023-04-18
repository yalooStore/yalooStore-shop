package com.yaloostore.shop.member.service.inter;

import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;
import com.yaloostore.shop.member.entity.Member;

import java.util.List;

public interface QueryMemberService {

    MemberSoftDeleteResponse softDeleteMember(Long memberId);
    MemberSoftDeleteResponse softDeleteLoginId(String loginId);

    Member findByLoginId(String loginId);

    MemberLoginResponse findMemberByLoginId(String loginId);


    /**
     * n일 후의 생일인 회원을 찾는 서비스 메소드
     *
     * @param lateDays 현재를 기준으로 +lateDays일 뒤를 계산합니다.
     * @return n일 후에 생일인 회원 목록을 dto 객체로 보냅니다.
     * */
    List<MemberIdResponse> findMemberByBirthday(int lateDays);


    /**
     * N일 후 생일인 회원을 찾는 서비스 메소드입니다.
     * 생년 월일이 아닌 월, 일만 입력해주면 해당 날짜에서 + n일 후의 생일이 회원 목록을 모두 불러옵니다.
     *
     * @param lateDays 현재를 기준으로 +lateDays일 뒤를 계산합니다.
     * @return n일 후에 생일인 회원 목록을 dto 객체로 보냅니다.
     * */
    List<MemberIdResponse> findMemberIdByLateDay(int lateDays);


}
