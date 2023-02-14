package com.yaloostore.shop.member.repository.jpa;

import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.member.repository.common.CommonMembershipRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface JpaMembershipRepository extends Repository<Membership, Long>, CommonMembershipRepository {
}
