package com.yaloostore.shop.member.dummy;


import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.Membership;
import com.yaloostore.shop.member.entity.MembershipHistory;

import java.time.LocalDateTime;

public class MembershipHistoryDummy {
    public static MembershipHistory dummy(Member member, Membership membership){

        return MembershipHistory.builder()
                .updateTime(LocalDateTime.now())
                .previousPaidAmount(0L)
                .member(member)
                .membership(membership)
                .build();
    }

}
