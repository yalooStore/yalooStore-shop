package com.yaloostore.shop.product.repository.querydsl;


import com.yaloostore.shop.product.dto.response.ProductFindResponse;

import com.yaloostore.shop.product.dto.response.ProductBookNewOneResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuerydslProductRepository {


    Optional<ProductFindResponse> queryFindProductById(Long id);


    Page<ProductFindResponse> queryFindAllProductsAdmin(Pageable pageable);

    Page<ProductFindResponse> queryFindAllProductsUser(Pageable pageable);

    /**
     * 쇼핑몰에 새로 입고된 순서대로(출판 시기대로 x) 새로 입고된 상품을 10개 나열해주는 메소드입니다.
     * */
    List<ProductBookNewOneResponse> queryFindProductNewOne();


}
