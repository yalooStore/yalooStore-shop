package com.yaloostore.shop.cart.service.impl;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.cart.dto.response.CartFindResponse;
import com.yaloostore.shop.cart.dto.response.CartSaveResponse;
import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.cart.repository.jpa.CartRepository;
import com.yaloostore.shop.cart.service.inter.CartService;
import com.yaloostore.shop.cart.service.inter.QuerydslCartService;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.querydsl.inter.QuerydslProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class CartServiceImplTest {


    private CartRepository cartRepository;
    private QuerydslMemberRepository querydslMemberRepository;
    private QuerydslProductRepository querydslProductRepository;
    private QuerydslCartService querydslCartService;

    private CartService cartService;


    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        querydslMemberRepository = mock(QuerydslMemberRepository.class);
        querydslProductRepository= mock(QuerydslProductRepository.class);
        querydslCartService = mock(QuerydslCartService.class);

        cartService = new CartServiceImpl(
                cartRepository,
                querydslMemberRepository,
                querydslProductRepository,
                querydslCartService
        );
    }

    @DisplayName("카트 저장 테스트 - 성공")
    @Test
    void save_success() {
        LocalDateTime now = LocalDateTime.now();

        //product service product mockito
        when(querydslProductRepository.queryFindByProductId(1L))
                .thenReturn(Optional.of(Product.builder()
                        .productId(1L).build()));

        //member service member mockito
        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test"))
                .thenReturn(Optional.of(Member.builder().memberId(1L).build()));

        when(cartRepository.save(any()))
                .thenReturn(Cart.builder()
                        .member(Member.builder()
                                .memberId(1L)
                                .build())
                        .product(Product.builder()
                                .productId(1L)
                                .build())
                        .placedInCartTime(now)
                        .build());

        CartSaveResponse response = cartService.save("test", 1L);

        assertThat(response.getPlacedInCartTime()).isEqualTo(now);
        assertThat(response.getProductId()).isEqualTo(1L);

    }
    @DisplayName("저장 테스트 실패- 존재하지 않는 회원 실패")
    @Test
    void save_MemberNotFound(){
        when(querydslProductRepository.queryFindByProductId(1L)).thenReturn(
                Optional.of(Product.builder().productId(1L).build()));
        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test")).thenThrow(new ClientException(ErrorCode.MEMBER_NOT_FOUND, "this member login id is not found"));

        assertThatThrownBy(()-> cartService.save("test", 1L)).isInstanceOf(ClientException.class);
    }

    @DisplayName("저장 테스트 실패- 존재하지 않는 상품 실패")
    @Test
    void save_ProductNotFound(){
        when(querydslProductRepository.queryFindByProductId(1L))
                .thenThrow(new ClientException(ErrorCode.PRODUCT_NOT_FOUND, "this product id is not found"));

        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test"))
                .thenReturn(Optional.of(Member.builder().memberId(1L).build()));

        assertThatThrownBy(()-> cartService.save("test", 1L)).isInstanceOf(ClientException.class);
    }

    @DisplayName("저장 테스트 실패 - 이미 존재하는 상품이 장바구니에 들어가는 경우")
    @Test
    void save_alreadyExistProduct(){
        when(querydslProductRepository.queryFindByProductId(1L))
                .thenReturn(Optional.of(Product.builder()
                        .productId(1L).build()));

        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test"))
                .thenReturn(Optional.of(Member.builder().memberId(1L).build()));

        when(querydslCartService.isExists(any(), eq(1L))).thenReturn(true);

        assertThatThrownBy(()-> cartService.save("test", 1L)).isInstanceOf(ClientException.class);
    }


    @DisplayName("회원 로그인 아이디와 상품 아이디로 해당 장바구니 상품을 찾기 테스트 - 성공")
    @Test
    void findById_success() {
        //given
        LocalDateTime now = LocalDateTime.now();

        when(querydslProductRepository.queryFindByProductId(1L))
                .thenReturn(Optional.of(Product.builder()
                        .productId(1L)
                        .build()));

        //member service member mockito
        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test"))
                .thenReturn(Optional.of(Member.builder()
                        .memberId(1L)
                        .id("test")
                        .build()));

        when(querydslCartService.isExists(any(),eq(1L))).thenReturn(true);


        when(cartRepository.findByMember_IdAndProduct_ProductId("test", 1L))
                .thenReturn(Cart.builder()
                        .member(Member.builder()
                                .memberId(1L)
                                .id("test")
                                .build())
                        .product(Product.builder()
                                .productId(1L)
                                .build())
                        .placedInCartTime(now)
                        .build());

        //when
        CartFindResponse response = cartService.findById("test", 1L);

        //then
        assertThat(response.getPlacedInCartTime()).isEqualTo(now);
        assertThat(response.getProductId()).isEqualTo(1L);
    }
    @DisplayName("회원 로그인 아이디와 상품 아이디로 해당 장바구니 상품을 찾기 테스트 - 회원이 존재하지 않는 경우(실패)")
    @Test
    void findById_MemberNotFound() {


        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test")).thenThrow(new ClientException(ErrorCode.MEMBER_NOT_FOUND, "not found member~"));

        when(querydslProductRepository.queryFindByProductId(1L))
                .thenReturn(Optional.of(Product.builder()
                        .productId(1L)
                        .build()));

        assertThatThrownBy(()->cartService.findById("test", 1L)).isInstanceOf(ClientException.class);

    }


    @DisplayName("회원 로그인 아이디와 상품 아이디로 해당 장바구니 상품을 찾기 테스트 - 상품이 존재하지 않는 경우(실패)")
    @Test
    void findById_ProductNotFound() {
        //given
        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test"))
                .thenReturn(Optional.of(Member.builder()
                        .memberId(1L)
                        .id("test")
                        .build()));

        when(querydslProductRepository.queryFindByProductId(1L)).thenThrow(new ClientException(ErrorCode.PRODUCT_NOT_FOUND, "not found product"));


        //when, then
        assertThatThrownBy(()->cartService.findById("test", 1L)).isInstanceOf(ClientException.class);

    }

    @DisplayName("회원 로그인 아이디와 상품 아이디로 해당 장바구니 상품을 찾기 테스트 - 장바구니에 찾고자하는 상품이 없는 경우(실패)")
    @Test
    void findById_NotExistProductInCart() {

        when(querydslProductRepository.queryFindByProductId(1L))
                .thenReturn(Optional.of(Product.builder()
                        .productId(1L)
                        .build()));

        //member service member mockito
        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test"))
                .thenReturn(Optional.of(Member.builder()
                        .memberId(1L)
                        .id("test")
                        .build()));

        when(querydslCartService.isExists(any(), eq(1L))).thenReturn(false);

        assertThatThrownBy(()-> cartService.findById("test",eq(1L))).isInstanceOf(ClientException.class);
    }


    @DisplayName("삭제 - 성공")
    @Test
    void delete_success() {
        when(querydslMemberRepository.queryFindUndeletedMemberLoginId("test"))
                .thenReturn(Optional.of(Member.builder()
                        .memberId(1L)
                        .id("test")
                        .build()));

        when(querydslProductRepository.queryFindByProductId(1L))
                .thenReturn(Optional.of(Product.builder()
                        .productId(1L)
                        .build()));

        when(querydslCartService.isExists(any(), eq(1L))).thenReturn(true);


        cartService.delete("test", 1L);

        verify(cartRepository,atLeastOnce()).deleteByMember_MemberIdAndProduct_ProductId(1L, 1L);

    }


    @DisplayName("삭제 - 해당 회원 없는 경우(실패)")
    @Test
    void delete_NotFoundMember() {

    }

    @DisplayName("삭제 - 해당 상품 장바구니에 없는 경우(실패)")
    @Test
    void delete_NotFoundProductInCart() {

    }
}