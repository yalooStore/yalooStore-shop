package com.yaloostore.shop.cart.service.inter;

import com.yaloostore.shop.cart.dto.response.CartSaveResponse;
import com.yaloostore.shop.cart.entity.Cart;

import java.util.Optional;

public interface CartService {


    CartSaveResponse save(String loginId, Long productId);
    Optional<Cart> findById(Long memberId, Long productId);

    void delete(String loginId, Long productId);
}
