package com.yaloostore.shop.product.product_type.service;

import com.yaloostore.shop.product.product_type.entity.ProductType;

import java.util.List;
import java.util.Optional;

public interface ProductTypeService {

    /**
     * 모든 상품 타입을 조회하는 메서드
     * @return 모든 상품 유형을 리스트로 반환
     * */
    List<ProductType> getAllProductTypeList();

    Optional<ProductType> getProductType(Long productTypeNo);


}
