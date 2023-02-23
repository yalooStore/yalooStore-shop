package com.yaloostore.shop.coupon.entity;


import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/**
 * 회원이 가지고 있는 쿠폰 정보를 저장하는 엔티티입니다.
 *
 * - MemberCoupon - 사용하지 않은 쿠폰으로 회원이 가지고 있는 쿠폰을 저장하는 엔티티를 의미한다.
 * - MemberOrderCoupon - 주문에 사용한 쿠폰 정보를 저장하는 엔티티를 의미한다.
 * */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name ="member_coupons")
@Getter
@Builder
public class MemberCoupon {


    @Id
    @Column(name = "coupon_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long couponId;

    @Column(name = "coupon_code",nullable = false, unique = true)
    private String couponCode;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(name = "coupon_expired_date_time",nullable = false)
    private LocalDateTime couponExpiredDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 쿠폰 사용할 때 사용하는 메소드입니다.
     * */
    public void couponUse(){
        if(this.isUsed){
            throw new ClientException(ErrorCode.BAD_REQUEST,
                    "this coupon is used");
        }
        this.isUsed = true;
    }


}
