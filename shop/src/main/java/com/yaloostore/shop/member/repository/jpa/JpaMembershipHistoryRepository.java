package com.yaloostore.shop.member.repository.jpa;

import com.yaloostore.shop.member.entity.MembershipHistory;
import com.yaloostore.shop.member.repository.common.CommonMembershipHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface JpaMembershipHistoryRepository extends Repository<MembershipHistory, Long>, CommonMembershipHistoryRepository {
}
