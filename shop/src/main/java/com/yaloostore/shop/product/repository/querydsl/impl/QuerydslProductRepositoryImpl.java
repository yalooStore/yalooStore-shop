package com.yaloostore.shop.product.repository.querydsl.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.book.entity.QBook;
import com.yaloostore.shop.product.dto.response.ProductFindResponse;
import com.yaloostore.shop.product.dto.response.ProductBookNewStockResponse;
import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.common.ProductTypeCode;
import com.yaloostore.shop.product.entity.QProduct;
import com.yaloostore.shop.product.repository.querydsl.inter.QuerydslProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.support.PageableExecutionUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class QuerydslProductRepositoryImpl implements QuerydslProductRepository {

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
                                product.discountPrice,
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
                                qProduct.discountPrice,
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
                                qProduct.discountPrice,
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

    /**
     * {@inheritDoc}
     * */
    @Override
    public List<ProductBookNewStockResponse> queryFindProductNewOne() {
        QProduct product =QProduct.product;
        QBook book = QBook.book;

        return factory.from(product)
                .rightJoin(book)
                .select(Projections.constructor(ProductBookNewStockResponse.class,
                product.productId,
                product.productName,
                product.description,
                product.thumbnailUrl,
                product.discountPrice,
                product.rawPrice,
                product.discountPercent,
                book.isbn,
                book.publisherName,
                book.authorName))
                .where(product.productId.eq(book.product.productId).
                        and(product.isExpose.eq(true))
                        .and(product.rawPrice.gt(0)))
                .orderBy(product.productCreatedAt.desc())
                .limit(10)
                .fetch();

    }

    @Override
    public Page<Product> queryFindAllProduct(Pageable pageable) {
        QProduct product = QProduct.product;

        List<Product> productList = factory.select(product)
                .from(product)
                .where(product.isExpose.isTrue()
                        .and(product.isSelled.isTrue()
                                .and(product.rawPrice.gt(0))))
                .orderBy(product.productCreatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = factory.select(product.count())
                .from(product)
                .where(product.isDeleted.isFalse()
                        .and(product.isSelled.isTrue()
                        .and(product.rawPrice.gt(0))));

        return PageableExecutionUtils.getPage(productList, pageable, countQuery::fetchFirst);
    }

//    @Override
//    public Page<ProductBookResponseDto> queryFindAllProductByProductType(Pageable pageable, Integer typeId) {
//        QProduct product= QProduct.product;
//        QBook book = QBook.book;
//
//        Optional<ProductTypeCode> productType = Arrays.stream(ProductTypeCode.values())
//                .filter(value -> typeId.equals(value.getTypeId()))
//                .findAny();
//
//        //PRODUCT TYPE CODE NOT FOUND 던지는게 더 좋음
//        if(productType.isEmpty()){
//            throw new ClientException(
//                    ErrorCode.PRODUCT_NOT_FOUND,
//                    "Choosing the wrong code"
//            );
//        }
//
//        List<ProductBookResponseDto> productList = factory.select(Projections.constructor(ProductBookResponseDto.class,
//                product.productId,
//                product.productName,
//                product.stock,
//                product.productCreatedAt,
//                product.description,
//                product.thumbnailUrl,
//                product.fixedPrice,
//                product.rawPrice,
//                product.isSelled,
//                product.isDeleted,
//                product.isExpose,
//                product.discountPercent,
//                book.publisherName,
//                book.authorName,
//                book.isbn,
//                book.pageCount))
//                .from(product)
//                .where(product.isExpose.isTrue()
//                        .and(product.isSelled.isTrue()
//                        //데이터 삽입 중 0원 절판된 경우가 있을 수 있기에 이 부분을 한번 더 확인해준다.
//                        .and(product.rawPrice.gt(0))
//                        .and(product.isDeleted.isFalse()
//                        .and(product.productTypeCode.eq(productType.get())))))
//                .orderBy(product.productCreatedAt.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        JPAQuery<Long> queryCount = factory.select(product.count())
//                .from(product)
//                .where(product.isExpose.isTrue()
//                        .and(product.isSelled.isTrue()
//                                .and(product.rawPrice.gt(0))));
//        return PageableExecutionUtils.getPage(productList, pageable,queryCount::fetchFirst);
//    }

 @Override
    public Page<Product> queryFindAllProductByProductType(Pageable pageable, Integer typeId) {
        QProduct product= QProduct.product;

        Optional<ProductTypeCode> productType = Arrays.stream(ProductTypeCode.values())
                .filter(value -> typeId.equals(value.getTypeId()))
                .findAny();

        //PRODUCT TYPE CODE NOT FOUND 던지는게 더 좋음
        if(productType.isEmpty()){
            throw new ClientException(
                    ErrorCode.PRODUCT_NOT_FOUND,
                    "Choosing the wrong code"
            );
        }

        List<Product> productList = factory.select(product)
                .from(product)
                .where(product.isExpose.isTrue()
                        .and(product.isSelled.isTrue()
                        //데이터 삽입 중 0원 절판된 경우가 있을 수 있기에 이 부분을 한번 더 확인해준다.
                        .and(product.rawPrice.gt(0))
                        .and(product.isDeleted.isFalse()
                        .and(product.productTypeCode.eq(productType.get())))))
                .orderBy(product.productCreatedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> queryCount = factory.select(product.count())
                .from(product)
                .where(product.isExpose.isTrue()
                        .and(product.isSelled.isTrue()
                                .and(product.rawPrice.gt(0))));
        return PageableExecutionUtils.getPage(productList, pageable,queryCount::fetchFirst);
    }

    @Override
    public Optional<Product> queryFindByProductId(Long productId) {
        QProduct product =QProduct.product;


        return Optional.ofNullable(factory
                .select(product)
                .from(product)
                .where(product.productId.eq(productId)
                        .and(product.isDeleted.isFalse()
                        .and(product.isSelled.isFalse()
                        .and(product.isExpose.isTrue())))).fetchOne());
    }


}
