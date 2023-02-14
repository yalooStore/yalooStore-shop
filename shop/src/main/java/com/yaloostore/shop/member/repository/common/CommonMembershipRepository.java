package com.yaloostore.shop.member.repository.common;

import com.yaloostore.shop.member.entity.Membership;

import java.util.Optional;

public interface CommonMembershipRepository {

    void deleteById(Long membershipId);

    Optional<Membership> findById(Long membershipId);

    int count();

    Membership save(Membership membership);
}
