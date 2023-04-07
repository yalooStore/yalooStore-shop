package com.yaloostore.shop.product.repository.elasticSearch.common;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yaloostore.shop.product.documents.SearchProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


/**
 * 엘라스틱 서치를 사용해서 상품 검색을 위한 레포지토리입니다.
 * */


public interface ElasticCommonProductRepository extends ElasticsearchRepository<SearchProduct, Long> {

    Page<SearchProduct> findByProductName(Pageable pageable, String productName);

    Optional<SearchProduct> findById(Long productId);



}
