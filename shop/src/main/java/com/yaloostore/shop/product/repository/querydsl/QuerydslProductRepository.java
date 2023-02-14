package com.yaloostore.shop.product.repository.querydsl;


import com.yaloostore.shop.product.dto.response.ProductFindResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuerydslProductRepository {


    Optional<ProductFindResponse> queryFindProductById(Long id);


    Page<ProductFindResponse> queryFindAllProductsAdmin(Pageable pageable);

    Page<ProductFindResponse> queryFindAllProductsUser(Pageable pageable);



}
