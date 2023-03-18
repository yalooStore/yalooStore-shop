package com.yaloostore.shop.cart.repository.querydsl;

import com.yaloostore.shop.cart.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuerydslCartRepository {


    /**
     * 해당 카트안에 상품이 들어있는지 확인하는 메소드입니다.
     * */
    Boolean queryExistCartByMemberIdAndProductId(Long memberId, Long productId);


    /**
     * 해당 회원 아이디(Pk)값을 이용해서 장바구니를 조회하는 메소드입니다.
     * */
    Page<Cart> queryFindCartByMemberId(Long memberId, Pageable pageable);

}
