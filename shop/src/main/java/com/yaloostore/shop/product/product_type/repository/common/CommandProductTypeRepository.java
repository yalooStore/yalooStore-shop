package com.yaloostore.shop.product.product_type.repository.common;

import com.yaloostore.shop.product.product_type.entity.ProductType;

import java.util.List;
import java.util.Optional;

public interface CommandProductTypeRepository {

    ProductType save(ProductType productType);
    Optional<ProductType> findByProductTypeNo(Long productTypeNo);

    List<ProductType> findAll();
}
