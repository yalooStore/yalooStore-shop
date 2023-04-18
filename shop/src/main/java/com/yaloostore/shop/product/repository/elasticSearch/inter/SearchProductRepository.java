package com.yaloostore.shop.product.repository.elasticSearch.inter;


import co.elastic.clients.elasticsearch._types.aggregations.ParentAggregate;
import com.yaloostore.shop.product.documents.SearchProduct;
import com.yaloostore.shop.product.documents.SearchProductBook;
import com.yaloostore.shop.product.dto.response.SearchProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 상품 검색 레포지토리 인터페이스
 * */
public interface SearchProductRepository {

    /**
     * 상품명으로 해당하는 상품을 검색하는 메소드입니다.
     * */
    Page<SearchProduct> searchProductsByProductName(String productName, Pageable pageable);



}
