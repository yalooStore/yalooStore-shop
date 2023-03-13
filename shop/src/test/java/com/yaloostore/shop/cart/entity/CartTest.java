package com.yaloostore.shop.cart.entity;

import com.yaloostore.shop.member.dummy.MemberDummy;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Cart Entity 테스트 클래스입니다.
 * */
class CartTest {

    @Test
    void create() {

        //given
        Member member = MemberDummy.dummy();
        Product product = ProductDummy.dummy();

        LocalDateTime nowTime = LocalDateTime.now();

        //when
        Cart cart = Cart.create(member, product);


        //then
        assertThat(cart).isNotNull();
        assertThat(cart.getMember().getMemberId()).isEqualTo(member.getMemberId());
        assertThat(cart.getProduct().getProductId()).isEqualTo(product.getProductId());
    }
}