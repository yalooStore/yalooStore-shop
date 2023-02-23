package com.yaloostore.shop.coupon.dummy;

import com.yaloostore.shop.coupon.entity.MemberCoupon;
import com.yaloostore.shop.member.entity.Member;

import java.time.LocalDateTime;
import java.time.Month;

public class MemberCouponDummy {

    public static MemberCoupon dummy(Member member){
        return MemberCoupon.builder()
                .couponCode("test code")
                .isUsed(false)
                .couponExpiredDateTime(LocalDateTime.of(2023, Month.NOVEMBER,1,0,0,0))
                .member(member)
                .build();
    }

    public static MemberCoupon dummy_used(Member member){
        return MemberCoupon.builder()
                .couponCode("test used coupon")
                .isUsed(true)
                .couponExpiredDateTime(LocalDateTime.of(2023, Month.NOVEMBER,1,0,0,0))
                .member(member)
                .build();
    }


}
