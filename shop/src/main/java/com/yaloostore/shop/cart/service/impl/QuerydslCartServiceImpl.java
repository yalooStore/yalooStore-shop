package com.yaloostore.shop.cart.service.impl;

import com.yaloostore.shop.cart.dto.response.CartResponseDto;
import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.cart.repository.querydsl.QuerydslCartRepository;
import com.yaloostore.shop.cart.service.inter.QuerydslCartService;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class QuerydslCartServiceImpl implements QuerydslCartService {

    private final QueryMemberService queryMemberService;
    private final QuerydslCartRepository querydslCartRepository;

    @Override
    public Boolean isExists(String loginId, Long productId) {
        Member member = queryMemberService.findByLoginId(loginId);

        return querydslCartRepository.queryExistCartByMemberIdAndProductId(member.getMemberId(), productId);
    }

    @Override
    public PaginationResponseDto<CartResponseDto> findCartByMemberLoginId(String loginId, Pageable pageable) {

        Member member = queryMemberService.findByLoginId(loginId);

        Page<Cart> page = querydslCartRepository.queryFindCartByMemberId(member.getMemberId(), pageable);

        return getResponse(page);
    }

    private PaginationResponseDto<CartResponseDto> getResponse(Page<Cart> page) {

        List<CartResponseDto> list = new ArrayList<>();

        for (Cart cart : page) {
            list.add(CartResponseDto.builder()
                            .productId(cart.getPk().getProductId())
                            .productName(cart.getProduct().getProductName())
                            .thumbnailUrl(cart.getProduct().getThumbnailUrl())
                            .rawPrice(cart.getProduct().getRawPrice())
                            .discountPrice(cart.getProduct().getDiscountPrice())
                            .discountPercent(cart.getProduct().getDiscountPercent())
                            .publisherName(cart.getProduct().getBook().getPublisherName())
                            .authorName(cart.getProduct().getBook().getAuthorName())
                    .build());

        }
        return PaginationResponseDto.<CartResponseDto>builder()
                .currentPage(page.getNumber())
                .totalDataCount(page.getTotalElements())
                .totalPage(page.getTotalPages())
                .dataList(list)
                .build();
    }


}
