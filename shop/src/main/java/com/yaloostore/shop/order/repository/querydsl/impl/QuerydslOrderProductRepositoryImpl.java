package com.yaloostore.shop.order.repository.querydsl.impl;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.book.entity.QBook;
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

        /**
         * TODO : order_product table에서 중복되는 product id 를 사용해서 (주문취소등은 고려X)
         * 개수대로 가져오는 작업으로만 일단은 진행
         *
         * select b.author_name, b.book_created_at, b.ebook_url, p.product_id
         * from order_products as op
         * right join products as p
         * 	on p.product_id = op.product_id
         * right join book as b
         * 	on p.product_id = b.product_id
         * where p.product_id = (SELECT count(p.product_id) from products as p)
         *
         * -> db 작성 연습을 열심히하자 !!
         * */
        return queryFactory.select(Projections.constructor(BestSellerResponse.class,
                        product.productName,
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
                    .rightJoin(product)
                    .on(orderProduct.product.productId.eq(product.productId))
                    .rightJoin(book)
                    .on(product.productId.eq(book.product.productId))
                    .where(product.productId.eq(queryFactory.select(product.productId.count()).from(product)))
                    .limit(10)
                    .fetch();

    }
}
