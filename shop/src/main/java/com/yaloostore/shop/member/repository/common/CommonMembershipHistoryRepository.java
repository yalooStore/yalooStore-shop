package com.yaloostore.shop.member.repository.common;

import com.yaloostore.shop.member.entity.MembershipHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonMembershipHistoryRepository {

    int count();

    MembershipHistory save(MembershipHistory membershipHistory);
}
