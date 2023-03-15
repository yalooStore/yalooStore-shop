package com.yaloostore.shop.member.repository.basic;

import com.yaloostore.shop.member.entity.MembershipHistory;
import com.yaloostore.shop.member.repository.common.CommonMembershipHistoryRepository;
import org.springframework.data.repository.Repository;

public interface MembershipHistoryRepository extends Repository<MembershipHistory, Long>, CommonMembershipHistoryRepository {
}
