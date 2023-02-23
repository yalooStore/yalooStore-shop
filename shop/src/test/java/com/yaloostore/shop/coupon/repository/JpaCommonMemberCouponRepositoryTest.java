package com.yaloostore.shop.coupon.repository;

import com.yaloostore.shop.coupon.dummy.MemberCouponDummy;
import com.yaloostore.shop.coupon.entity.MemberCoupon;
import com.yaloostore.shop.coupon.repository.jpa.JpaCommonMemberCouponRepository;
import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaCommonMemberCouponRepositoryTest {


    @Autowired
    JpaCommonMemberCouponRepository jpaCommonMemberCouponRepository;

    @Autowired
    TestEntityManager entityManager;


    private Member member;

    private MemberCoupon coupon;
    private MemberCoupon usedCoupon;


    @BeforeEach
    void setUp(){
        member = MemberDummy.dummy();

        coupon = MemberCouponDummy.dummy(member);

        entityManager.persist(coupon);
    }


    @DisplayName("회원 쿠폰 저장 테스트")
    @Test
    void testSave(){
        //then
        MemberCoupon savedCoupon = jpaCommonMemberCouponRepository.save(coupon);


        //when
        assertThat(savedCoupon.getCouponCode()).isEqualTo(coupon.getCouponCode());
        assertThat(savedCoupon.isUsed()).isFalse();
    }
}