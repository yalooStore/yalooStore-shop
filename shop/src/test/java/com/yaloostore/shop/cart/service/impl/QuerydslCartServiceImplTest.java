package com.yaloostore.shop.cart.service.impl;

import com.yaloostore.shop.cart.dto.response.CartFindResponse;
import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.cart.repository.querydsl.QuerydslCartRepository;
import com.yaloostore.shop.cart.service.inter.CartService;
import com.yaloostore.shop.cart.service.inter.QuerydslCartService;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.service.impl.ProductServiceImpl;
import com.yaloostore.shop.product.service.inter.ProductService;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.management.Query;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuerydslCartServiceImplTest {

    private QueryMemberService queryMemberService;
    private QuerydslCartRepository querydslCartRepository;
    private ProductService productService;
    private CartService cartService;

    private QuerydslCartService querydslCartService;



    @BeforeEach
    void setUp() {

        queryMemberService = mock(QueryMemberService.class);
        querydslCartRepository = mock(QuerydslCartRepository.class);
        querydslCartService = new QuerydslCartServiceImpl(queryMemberService, querydslCartRepository);
        productService = mock(ProductServiceImpl.class);
        cartService=mock(CartService.class);
    }


    @DisplayName("찾는 상품이 장바구니에 존재 여부 테스트")
    @Test
    void isExists() {

        LocalDateTime now = LocalDateTime.now();
        when(queryMemberService.findByLoginId("test")).thenReturn(
                Member.builder()
                        .memberId(1L)
                        .id("test")
                        .build());

        when(querydslCartRepository.queryExistCartByMemberIdAndProductId(eq(1L),eq(1L))).thenReturn(true);


        when(querydslCartService.isExists("test", eq(1L))).thenReturn(true);
        Boolean result = querydslCartService.isExists("test", eq(1L));

        assertThat(result).isTrue();

    }

    
}