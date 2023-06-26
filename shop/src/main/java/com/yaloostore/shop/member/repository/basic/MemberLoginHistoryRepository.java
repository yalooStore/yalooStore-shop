package com.yaloostore.shop.member.repository.basic;

import com.yaloostore.shop.member.entity.MemberLoginHistory;
import com.yaloostore.shop.member.repository.common.CommonMemberLoginHistory;
import org.springframework.data.repository.Repository;

public interface MemberLoginHistoryRepository extends Repository<MemberLoginHistory, Long>, CommonMemberLoginHistory {
}
