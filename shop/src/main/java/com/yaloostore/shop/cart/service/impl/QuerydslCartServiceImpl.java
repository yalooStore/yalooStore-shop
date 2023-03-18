package com.yaloostore.shop.cart.service.impl;

import com.yaloostore.shop.cart.dto.response.CartResponseDto;
import com.yaloostore.shop.cart.repository.querydsl.QuerydslCartRepository;
import com.yaloostore.shop.cart.service.inter.QuerydslCartService;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.member.entity.Member;
import com.yaloostore.shop.member.service.inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        return null;
    }


}
