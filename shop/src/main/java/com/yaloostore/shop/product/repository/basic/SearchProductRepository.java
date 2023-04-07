package com.yaloostore.shop.product.repository.basic;


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

    Page<SearchProduct> searchProductsByProductName(String productName, Pageable pageable);


}
