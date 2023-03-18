package com.yaloostore.shop.cart.service.inter;

import com.yaloostore.shop.cart.dto.response.CartResponseDto;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import org.springframework.data.domain.Pageable;

public interface QuerydslCartService {

    Boolean isExists(String loginId, Long productId);

    PaginationResponseDto<CartResponseDto> findCartByMemberLoginId(String loginId, Pageable pageable);
}
