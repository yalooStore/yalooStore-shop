package com.yaloostore.shop.cart.service.impl;

import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.cart.dto.response.CartFindResponse;
import com.yaloostore.shop.cart.dto.response.CartSaveResponse;
import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.cart.repository.jpa.JpaCartCommonRepository;
import com.yaloostore.shop.cart.service.inter.CartService;
import com.yaloostore.shop.cart.service.inter.QuerydslCartService;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.repository.querydsl.inter.QuerydslMemberRepository;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.querydsl.inter.QuerydslProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {


    private final JpaCartCommonRepository jpaCartCommonRepository;
    private final QuerydslMemberRepository querydslMemberRepository;
    private final QuerydslProductRepository querydslProductRepository;
    private final QuerydslCartService querydslCartService;



    @Override
    @Transactional
    public CartSaveResponse save(String loginId, Long productId) {

        Product product = querydslProductRepository.queryFindByProductId(productId).orElseThrow(() ->
        {
            throw new ClientException(
                    ErrorCode.PRODUCT_NOT_FOUND,
                    "this product id is not found");
        });

        Member member = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId).orElseThrow(() ->
        {
            throw new ClientException(
                    ErrorCode.MEMBER_NOT_FOUND,
                    "this member login id is not found");
        });


        if(Boolean.TRUE.equals(querydslCartService.isExists(member.getId(), productId))){
            throw new ClientException(ErrorCode.CART_OVERLAPPING_EXISTS,
                    "this product already exists");
        }

        Cart cart = jpaCartCommonRepository.save(Cart.create(member, product));

        return CartSaveResponse.builder()
                .productId(productId)
                .placedInCartTime(cart.getPlacedInCartTime())
                .build();
    }

    @Override
    public CartFindResponse findById(String loginId, Long productId) {

        Product product = querydslProductRepository.queryFindByProductId(productId).orElseThrow(() ->
        {
            throw new ClientException(ErrorCode.PRODUCT_NOT_FOUND, "this product id is not found");
        });

        Member member = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId).orElseThrow(() -> {
            throw new ClientException(ErrorCode.MEMBER_NOT_FOUND,
                    "this member login id is not found");
        });

        if(Boolean.FALSE.equals(querydslCartService.isExists(member.getId(), productId))){
            throw new ClientException(ErrorCode.PRODUCT_NOT_FOUND, "this product is not exists");
        }
        Cart cart = jpaCartCommonRepository.findByMember_IdAndProduct_ProductId(member.getId(), productId);


        return CartFindResponse.builder()
                .productId(productId)
                .placedInCartTime(cart.getPlacedInCartTime())
                .build();
    }


    @Override
    @Transactional
    public void delete(String loginId, Long productId) {

        Member member = querydslMemberRepository.queryFindUndeletedMemberLoginId(loginId).orElseThrow(() -> {
            throw new ClientException(ErrorCode.MEMBER_NOT_FOUND, "this member is not found");
        });

        if(Boolean.FALSE.equals(querydslCartService.isExists(member.getId(), productId))){
            throw new ClientException(ErrorCode.PRODUCT_NOT_FOUND, "this product is not exists");
        }

        jpaCartCommonRepository.deleteByMember_MemberIdAndProduct_ProductId(member.getMemberId(), productId);


    }
}
