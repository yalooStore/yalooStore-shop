package com.yaloostore.shop.member.dummy;

import com.yaloostore.shop.member.common.Grade;
import com.yaloostore.shop.member.entity.Membership;

public class MembershipDummy {
    public static Membership dummy() {

        return Membership.builder()
                .grade(Grade.WHITE)
                .membershipStandardAmount(0L)
                .membershipPoint(10_000L)
                .build();
    }
}
