package com.yaloostore.shop.cart.repository.common;

import com.yaloostore.shop.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommonCartRepository {

    /**
     * cart 저장 메소드
     * */
    Cart save(Cart cart);



    /**
     * @param memberId
     * @param productId
     * @return
     *
     * memberId productId 값으로 특정 cart 객체를 찾아오는 메소드
     * */
    Optional<Cart> findByMember_MemberIdAndProduct_ProductId(Long memberId, Long productId);

    Cart findByMember_IdAndProduct_ProductId(String loginId, Long productId);


    /**
     * @param memberId
     * @param productId
     * memberId productId 값으로 특정 cart 객체 삭제하는 메소드
     * */
    void deleteByMember_MemberIdAndProduct_ProductId(Long memberId, Long productId);
}
