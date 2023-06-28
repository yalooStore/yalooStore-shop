package com.yaloostore.shop.member.service.inter;

import com.yaloostore.shop.member.dto.response.MemberDuplicateDto;
import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginResponse;
import com.yaloostore.shop.member.dto.response.MemberSoftDeleteResponse;
import com.yaloostore.shop.member.dto.transfer.MemberDto;
import com.yaloostore.shop.member.entity.Member;

import java.util.List;
import java.util.Optional;

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

    /**
     * 회원 닉네임을 기준으로 해당 회원을 찾아오는 메소드입니다.
     * @param nickname 회원 닉네임
     * @return 해당 닉네임을 가진 회원 dto 객체
     * */
    MemberDto findMemberByNickname(String nickname);

    /**
     * 회원 휴대전화번호를 기준으로 해당 회원을 찾아오는 메소드입니다.
     * @param phoneNumber 회원 닉네임
     * @return 해당 휴대전화번호를 가진 회원 dto 객체
     * */
    MemberDto findMemberByPhoneNumber(String phoneNumber);

    /**
     * 회원 이메일을 기준으로 해당 회원을 찾아오는 메소드입니다.
     * @param email 회원 닉네임
     * @return 해당 이메일을 가진 회원 dto 객체
     * */
    MemberDto findMemberByEmail(String email);

    /**
     * 회원 휴대전화번호를 기준으로 해당 회원이 존재하는지 확인하는 메소드입니다.
     * @param nickname 회원 휴대전화번호
     * @return 회원 중복 여부
     * */
    MemberDuplicateDto existMemberByNickname(String nickname);

    /**
     * 회원 휴대전화번호를 기준으로 해당 회원이 존재하는지 확인하는 메소드입니다.
     * @param phoneNumber 회원 휴대전화번호
     * @return 회원 중복 여부
     * */
    MemberDuplicateDto existMemberByPhoneNumber(String phoneNumber);

    /**
     * 회원 휴대전화번호를 기준으로 해당 회원이 존재하는지 확인하는 메소드입니다.
     * @param email 회원 휴대전화번호
     * @return 회원 중복 여부
     * */
    MemberDuplicateDto existMemberByEmail(String email);



}
