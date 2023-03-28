package com.yaloostore.shop.cart.service.impl;

import com.yaloostore.shop.book.dummy.BookDummy;
import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.cart.dto.response.CartFindResponse;
import com.yaloostore.shop.cart.dto.response.CartResponseDto;
import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.cart.repository.querydsl.QuerydslCartRepository;
import com.yaloostore.shop.cart.service.inter.CartService;
import com.yaloostore.shop.cart.service.inter.QuerydslCartService;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.management.Query;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuerydslCartServiceImplTest {

    private QueryMemberService queryMemberService;
    private QuerydslCartRepository querydslCartRepository;
    private ProductService productService;
    private CartService cartService;

    private QuerydslCartService querydslCartService;

    private Member member;
    private Product product;

    private Book book;



    @BeforeEach
    void setUp() {

        queryMemberService = mock(QueryMemberService.class);
        querydslCartRepository = mock(QuerydslCartRepository.class);
        querydslCartService = new QuerydslCartServiceImpl(queryMemberService, querydslCartRepository);
        productService = mock(ProductServiceImpl.class);
        cartService=mock(CartService.class);

        Product product2 = Product.builder()
                .productId(1L)
                .book(book)
                .build();
        book = BookDummy.dummy2(product2);


        product = Product.builder()
                .productId(2L)
                .book(book)
                .build();


        member = Member.builder().memberId(1L).id("test").build();



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

    @DisplayName("로그인 회원 아이디를 사용해서 해당 장바구니를 가져와 dto 객체로 돌려주는 메소드 테스트")
    @Test
    void findCartByMemberLoginId(){
        //given
        when(queryMemberService.findByLoginId("test")).thenReturn(member);


        when(querydslCartRepository.queryFindCartByMemberId(1L, PageRequest.of(0,5)))
                .thenReturn(new PageImpl<>(List.of(Cart.create(member, product))));


        PaginationResponseDto<CartResponseDto> responseDto = querydslCartService.findCartByMemberLoginId("test", PageRequest.of(0, 5));

        assertThat(responseDto.getTotalDataCount()).isEqualTo(1L);
        assertThat(responseDto.getDataList().isEmpty()).isFalse();

    }


}