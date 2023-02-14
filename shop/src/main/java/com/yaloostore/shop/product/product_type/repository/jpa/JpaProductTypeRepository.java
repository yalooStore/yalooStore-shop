package com.yaloostore.shop.product.product_type.repository.jpa;

import com.yaloostore.shop.product.product_type.entity.ProductType;
import com.yaloostore.shop.product.product_type.repository.common.CommandProductTypeRepository;
import org.springframework.data.repository.Repository;

public interface JpaProductTypeRepository extends Repository<ProductType, Long>, CommandProductTypeRepository {



}
