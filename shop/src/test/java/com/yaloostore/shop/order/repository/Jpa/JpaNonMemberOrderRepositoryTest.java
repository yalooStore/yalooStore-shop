package com.yaloostore.shop.order.repository.Jpa;


import com.yaloostore.shop.coupon.dummy.MemberCouponDummy;
import com.yaloostore.shop.coupon.entity.MemberCoupon;
import com.yaloostore.shop.member.dummy.MemberAddressDummy;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.entity.MemberAddress;
import com.yaloostore.shop.order.entity.MemberOrder;
import com.yaloostore.shop.order.entity.MemberOrderCoupon;
import com.yaloostore.shop.order.repository.jpa.JpaMemberOrderCouponCommonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 비회원일 경우 주문 레포지토리 테스트
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class JpaNonMemberOrderRepositoryTest {


    @Autowired
    JpaMemberOrderCouponCommonRepository repository;

    @Autowired
    TestEntityManager testEntityManager;

    private Member member;
    private MemberOrder memberOrder;
    private MemberCoupon memberCoupon;
    private MemberOrderCoupon memberOrderCoupon;


    @BeforeEach
    void setUp(){
        member = MemberDummy.dummy();


        memberOrder = MemberOrder.builder()
                .memberAddress(MemberAddressDummy.dummy(member))
                .member(member)
                .build();
        memberCoupon = MemberCouponDummy.dummy(member);

        testEntityManager.persist(memberOrder);
        testEntityManager.persist(memberCoupon);

    }
    @DisplayName("회원 주문에 사용된 쿠폰 저장 테스트")
    @Test
    void testMemberOrderCouponSave(){
        //given
        memberOrderCoupon = MemberOrderCoupon.create(memberOrder,memberCoupon);

        //when
        MemberOrderCoupon savedOrderCoupon = repository.save(memberOrderCoupon);

        //then
        assertThat(savedOrderCoupon.getMemberCoupon()).isEqualTo(memberCoupon);
        assertThat(savedOrderCoupon.getMemberOrder()).isEqualTo(memberOrder);
    }


}
