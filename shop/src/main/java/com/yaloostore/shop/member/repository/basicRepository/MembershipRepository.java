package com.yaloostore.shop.member.repository.basicRepository;

import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.member.repository.common.CommonMembershipRepository;
import org.springframework.data.repository.Repository;

public interface MembershipRepository extends Repository<Membership, Long>, CommonMembershipRepository {
}
