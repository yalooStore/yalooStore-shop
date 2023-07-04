package com.yaloostore.shop.member.service.inter;

import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;
import com.yaloostore.shop.member.entity.MemberLoginHistory;

public interface MemberLoginHistoryService {


    /**
     * 로그인 시 해당 로그인 기록이 저장될 수 있게 하는 비지니스 로직입니다.
     *
     * @param loginId 로그인 아이디를 사용해 로그인 기록을 저장할 수 있게 합니다.
     * @return 로그인 기록 dto 객체
     * */
    MemberLoginHistoryResponse addLoginHistory(String loginId);
}
