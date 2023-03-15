package com.yaloostore.shop.order.repository.Jpa;

import com.yaloostore.shop.coupon.dummy.MemberCouponDummy;
import com.yaloostore.shop.coupon.entity.MemberCoupon;
import com.yaloostore.shop.member.dummy.MemberAddressDummy;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.order.entity.MemberOrder;
import com.yaloostore.shop.order.entity.MemberOrderCoupon;
import com.yaloostore.shop.order.repository.basic.MemberOrderCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberOrderCouponCommonRepositoryTest {


    @Autowired
    MemberOrderCouponRepository repository;

    @Autowired
    TestEntityManager entityManager;

    private Member member;
    private MemberOrder memberOrder;
    private MemberCoupon memberCoupon;
    private MemberOrderCoupon memberOrderCoupon;

    @BeforeEach
    void setUp(){
        member = MemberDummy.dummy();
        entityManager.persist(member);

        memberCoupon = MemberCouponDummy.dummy(member);
        memberOrder = MemberOrder.builder()
                .memberAddress(MemberAddressDummy.dummy(member))
                .member(member)
                .build();
        entityManager.persist(memberCoupon);
        entityManager.persist(memberOrder);
    }


    @DisplayName("주문에 사용한 쿠폰 저장 테스트")
    @Test
    void testSave(){
        //given
        memberOrderCoupon = MemberOrderCoupon.create(memberOrder, memberCoupon);

        //when
        MemberOrderCoupon savedOrderCoupon = repository.save(memberOrderCoupon);


        //then
        assertThat(savedOrderCoupon).isNotNull();
        assertThat(savedOrderCoupon.getMemberOrder().getOrderCode()).isEqualTo(memberOrder.getOrderCode());
        assertThat(savedOrderCoupon.getMemberCoupon().getCouponCode()).isEqualTo(memberCoupon.getCouponCode());


    }
}