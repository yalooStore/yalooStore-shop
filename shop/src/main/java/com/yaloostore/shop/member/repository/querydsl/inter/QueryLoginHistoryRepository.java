package com.yaloostore.shop.member.repository.querydsl.inter;

import com.yaloostore.shop.member.dto.response.MemberLoginHistoryResponse;

import java.time.LocalDate;
import java.util.List;

public interface QueryLoginHistoryRepository {

    List<MemberLoginHistoryResponse> findSleepAccountByLoginHistory(LocalDate start);

}
