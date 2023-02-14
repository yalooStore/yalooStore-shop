package com.yaloostore.shop.product.repository.common;

import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.product_type.entity.ProductType;

import java.util.List;
import java.util.Optional;

public interface CommandProductRepository {
    Optional<Product> findByProductId(Long productId);

    void deleteByProductId(Long productId);

    List<Product> findAll();

    Product save(Product product);


}
