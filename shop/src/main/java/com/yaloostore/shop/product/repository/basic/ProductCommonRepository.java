package com.yaloostore.shop.product.repository.basic;



import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.common.CommonProductRepository;
import org.springframework.data.repository.Repository;


/**
 * JpaRepository -> Repository 상속으로 변경 사용
 * */

public interface ProductCommonRepository extends Repository<Product, Long>, CommonProductRepository {




}
