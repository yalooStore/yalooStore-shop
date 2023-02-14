package com.yaloostore.shop.product.product_type.repository.jpa;

import com.yaloostore.shop.product.product_type.entity.ProductTypeRegistration;
import com.yaloostore.shop.product.product_type.entity.ProductTypeRegistration.ProductTypeRegistrationPk;
import com.yaloostore.shop.product.product_type.repository.common.CommandProductTypeRegistrationRepository;
import org.springframework.data.repository.Repository;

public interface JpaProductTypeRegistrationRepository extends Repository<ProductTypeRegistration, ProductTypeRegistrationPk>, CommandProductTypeRegistrationRepository {

}
