package com.yaloostore.shop.order.repository.querydsl.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.book.QBook;
import com.yaloostore.shop.order.dto.response.BestSellerResponse;
import com.yaloostore.shop.order.entity.QOrderProduct;
import com.yaloostore.shop.order.repository.querydsl.inter.QuerydslOrderProductRepository;
import com.yaloostore.shop.product.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuerydslOrderProductRepositoryImpl implements QuerydslOrderProductRepository {


    private final JPAQueryFactory queryFactory;

    /**
     * product id가 같은 경우 최대
     * 초기 - 결제를 진행헀다는 가정하에 있는 OrderProduct에 있는 경우만 한정
     * */
    @Override
    public List<BestSellerResponse> queryFindAllByBestSeller() {

        QOrderProduct orderProduct = QOrderProduct.orderProduct;
        QProduct product = QProduct.product;
        QBook book = QBook.book;

        return queryFactory.select(Projections.constructor(BestSellerResponse.class, product.productName,
                                product.description,
                                product.thumbnailUrl,
                                product.fixedPrice,
                                book.isbn,
                                book.pageCount,
                                book.isEbook,
                                book.ebookUrl,
                                book.publisherName,
                                book.authorName))
                        .from(orderProduct)
                        .join(product)
                        .on(orderProduct.product.productId.eq(product.productId))
                        .join(book)
                        .on(product.productId.eq(book.product.productId))
                        .orderBy(product.productId.count().desc())
                        .limit(10)
                        .fetch();
    }
}
