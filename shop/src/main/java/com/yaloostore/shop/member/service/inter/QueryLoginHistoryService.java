package com.yaloostore.shop.member.service.inter;

import com.yaloostore.shop.member.entity.MemberLoginHistory;

import java.time.LocalDate;

public interface QueryLoginHistoryService {

    MemberLoginHistory addLoginHistory(String loginId, LocalDate now);

}
