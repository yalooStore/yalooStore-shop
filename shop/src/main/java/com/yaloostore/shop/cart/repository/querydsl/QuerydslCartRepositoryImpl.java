package com.yaloostore.shop.cart.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.cart.entity.QCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


/**
 * QueryDsl을 이용한 Cart Repository를 구현한 구현체입니다.
 * */
@Repository
@RequiredArgsConstructor
public class QuerydslCartRepositoryImpl implements QuerydslCartRepository{

    private final JPAQueryFactory factory;

    /**
     * {@inheritDoc}
     * */
    @Override
    public Boolean queryExistCartByMemberIdAndProductId(Long memberId, Long productId) {
        QCart cart = QCart.cart;
        return factory
                .selectFrom(cart)
                .where(cart.pk.memberId.eq(memberId)
                        .and(cart.pk.productId.eq(productId)
                        .and(cart.product.isDeleted.isFalse())))
                .fetchFirst()!=null;
    }
}
