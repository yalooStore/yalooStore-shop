package com.yaloostore.shop.product.repository.querydsl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaloostore.shop.book.QBook;
import com.yaloostore.shop.product.dto.response.ProductFindResponse;
import com.yaloostore.shop.product.entity.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.support.PageableExecutionUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

import static com.querydsl.jpa.JPAExpressions.select;

@Repository
@RequiredArgsConstructor
public class QuerydslProductRepositoryImpl implements QuerydslProductRepository{

    private final JPAQueryFactory factory;

    @Override
    public Optional<ProductFindResponse> queryFindProductById(Long id) {
        QProduct product = QProduct.product;
        QBook book = QBook.book;


        return Optional.ofNullable(
                factory.select(product)
                        .from(book)
                        .rightJoin(book.product, product)
                        .select(Projections.constructor(ProductFindResponse.class,
                                product.productName,
                                product.stock,
                                product.description,
                                product.fixedPrice,
                                product.rawPrice,
                                product.discountPercent,
                                book.isbn,
                                book.pageCount,
                                book.bookCreatedAt,
                                book.isEbook,
                                book.ebookUrl,
                                book.publisherName,
                                book.authorName))
                        .where(product.productId.eq(id))
                        .fetchOne());
    }

    @Override
    public Page<ProductFindResponse> queryFindAllProductsAdmin(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;

        JPQLQuery<ProductFindResponse> productFindAllResponseJPQLQuery =
                factory.from(qBook)
                        .rightJoin(qBook.product, qProduct)
                        .select(Projections.constructor(ProductFindResponse.class,
                                qProduct.productName,
                                qProduct.stock,
                                qProduct.description,
                                qProduct.fixedPrice,
                                qProduct.rawPrice,
                                qProduct.discountPercent,
                                qBook.isbn,
                                qBook.pageCount,
                                qBook.bookCreatedAt,
                                qBook.isEbook,
                                qBook.ebookUrl,
                                qBook.publisherName,
                                qBook.authorName));

        List<ProductFindResponse> productList = productFindAllResponseJPQLQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();


        return PageableExecutionUtils.getPage(productList,pageable,
                ()-> factory.from(qProduct).fetchCount());
    }

    @Override
    public Page<ProductFindResponse> queryFindAllProductsUser(Pageable pageable) {
        QProduct qProduct = QProduct.product;
        QBook qBook = QBook.book;



        JPQLQuery<ProductFindResponse> productFindAllResponseJPQLQuery =
                factory.from(qBook)
                        .rightJoin(qBook.product, qProduct)
                        .select(Projections.constructor(ProductFindResponse.class,
                                qProduct.productName,
                                qProduct.stock,
                                qProduct.description,
                                qProduct.fixedPrice,
                                qProduct.rawPrice,
                                qProduct.discountPercent,
                                qBook.isbn,
                                qBook.pageCount,
                                qBook.bookCreatedAt,
                                qBook.isEbook,
                                qBook.ebookUrl,
                                qBook.publisherName,
                                qBook.authorName))
                        .where(qProduct.isExpose.eq(true));


        List<ProductFindResponse> productList = productFindAllResponseJPQLQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();


        return PageableExecutionUtils.getPage(productList,pageable,
                ()-> factory.from(qProduct).fetchCount());
    }
}
