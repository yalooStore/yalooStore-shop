package com.yaloostore.shop.member.service.inter;

import com.yaloostore.shop.member.dto.response.MemberIdResponse;
import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.entity.MemberLoginHistory;

import java.time.LocalDate;
import java.util.List;

public interface MemberLoginHistoryService {


    /**
     * 로그인 시 해당 로그인 기록이 저장될 수 있게 하는 비지니스 로직입니다.
     *
     * @param loginId 로그인 아이디를 사용해 로그인 기록을 저장할 수 있게 합니다.
     * @return 로그인 기록 dto 객체
     * */
    MemberLoginHistoryResponse addLoginHistory(String loginId);


    /**
     * 로그인 시 저장된 로그인 기록을 비교하여 오늘로부터 1년이 넘게 로그인하지 않은 회원들을 가져옵니다.
     *
     * @param today 오늘로부터 1년전이 마지막 로그인 회원인 경우 모두 비회원 처리합니다.
     * @return
     * */
    List<MemberIdResponse> findMemberByLoginHistory(LocalDate today);

}
