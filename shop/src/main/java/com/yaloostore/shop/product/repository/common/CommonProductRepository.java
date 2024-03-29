package com.yaloostore.shop.product.repository.common;

import com.yaloostore.shop.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface CommonProductRepository {
    Optional<Product> findByProductId(Long productId);

    void deleteByProductId(Long productId);

    List<Product> findAll();

    Product save(Product product);


}
