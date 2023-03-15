package com.yaloostore.shop.cart.service.inter;

public interface QuerydslCartService {

    Boolean isExists(String loginId, Long productId);
}
