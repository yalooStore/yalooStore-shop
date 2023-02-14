package com.yaloostore.shop.product.repository.jpa;



import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.common.CommandProductRepository;
import org.springframework.data.repository.Repository;


/**
 * JpaRepository -> Repository 상속으로 변경 사용
 * */

public interface JpaProductRepository extends Repository<Product, Long>, CommandProductRepository {




}
