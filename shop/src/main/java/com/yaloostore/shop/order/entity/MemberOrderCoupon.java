package com.yaloostore.shop.order.entity;


import com.yaloostore.shop.coupon.entity.MemberCoupon;
import com.yaloostore.shop.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * 주문에 사용한 쿠폰과 관련된 정보를 위한 엔티티입니다.
 *
 * - MemberOrderCoupon - 주문에 사용한 쿠폰 정보를 저장하는 엔티티를 의미한다.
 * - MemberCoupon - 사용하지 않은 쿠폰으로 회원이 가지고 있는 쿠폰을 저장하는 엔티티를 의미한다.
 * */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member_order_coupons")
public class MemberOrderCoupon {
    @EmbeddedId
    private MemberOrderCouponPk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberOrderId")
    @JoinColumn(name = "member_order_id", nullable = false)
    private MemberOrder memberOrder;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("memberCouponId")
    @JoinColumn(name = "member_coupon_id",nullable = false)
    private MemberCoupon memberCoupon;

    /**
     * 주문에 사용한 쿠폰 엔티티 생성 메소드
     *
     * @param memberOrder
     * @param memberCoupon
     * @return 주문에 사용한 쿠폰 엔티티
     * */

    public static MemberOrderCoupon create(MemberOrder memberOrder, MemberCoupon memberCoupon){
        MemberOrderCouponPk orderCouponPk = new MemberOrderCouponPk(memberOrder.getOrderId(), memberCoupon.getCouponId());
        return new MemberOrderCoupon(orderCouponPk, memberOrder, memberCoupon);
    }


    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Embeddable
    public static class MemberOrderCouponPk implements Serializable {

        @Column(name = "member_order_id", nullable = false)
        private Long memberOrderId;

        @Column(name = "member_coupon_id", nullable = false)
        private Long memberCouponId;
    }
}
