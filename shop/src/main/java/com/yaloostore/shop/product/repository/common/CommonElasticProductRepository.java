package com.yaloostore.shop.product.repository.common;

import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.index.SearchProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * 엘라스틱 서치를 사용해서 상품 검색을 위한 레포지토리입니다.
 * */
public interface CommonElasticProductRepository extends ElasticsearchRepository<SearchProduct, Long> {


    Page<SearchProduct> findByProductName(Pageable pageable, String productName);



}
