package com.yaloostore.shop.cart.repository.querydsl;

public interface QuerydslCartRepository {


    /**
     * 해당 카트안에 상품이 들어있는지 확인하는 메소드입니다.
     * */
    Boolean queryExistCartByMemberIdAndProductId(Long memberId, Long productId);
}
