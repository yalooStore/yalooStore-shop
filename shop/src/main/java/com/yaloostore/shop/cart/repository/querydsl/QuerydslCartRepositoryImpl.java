package com.yaloostore.shop.cart.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.cart.entity.Cart;
import com.yaloostore.shop.cart.entity.QCart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


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

    @Override
    public Page<Cart> queryFindCartByMemberId(Long memberId, Pageable pageable) {

        QCart cart =QCart.cart;

        List<Cart> carts = factory.select(cart)
                .from(cart)
                .where(cart.pk.memberId.eq(memberId)
                        .and(cart.product.isDeleted.isFalse()
                        .and(cart.product.isExpose.isTrue())))
                .offset((long) pageable.getPageSize() * pageable.getPageNumber())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = factory.select(cart.pk.productId.count())
                .from(cart)
                .where(cart.pk.memberId.eq(memberId))
                .fetchOne();


        return new PageImpl<>(carts, pageable, count);
    }
}
