package com.yaloostore.shop.coupon;

import com.yalooStore.common_utils.exception.ClientException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MemberCouponTest {


    @DisplayName("쿠폰 사용 성공 테스트")
    @Test
    void testCouponUse_success() {

        //given
        MemberCoupon coupon = MemberCoupon.builder()
                .isUsed(false)
                .build();

        //when
        coupon.couponUse();

        //then
        assertThat(coupon.isUsed()).isTrue();

    }


    @DisplayName("쿠폰 사용 실패 테스트 - 실패시 클라이언트관련 예외를 던진다.")
    @Test
    void testCouponUse_fail() {

        //given
        MemberCoupon coupon = MemberCoupon.builder()
                .isUsed(true)
                .build();

        //when, then
        assertThatThrownBy(()-> coupon.couponUse()).isInstanceOf(ClientException.class);
    }



}