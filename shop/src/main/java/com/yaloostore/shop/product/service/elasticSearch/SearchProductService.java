package com.yaloostore.shop.product.service.elasticSearch;


import com.yaloostore.shop.product.dto.response.SearchProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SearchProductService {

    /**
     * 상품 이름으로 찾은 객체를 (Page)돌려주는 메소드 입니다.
     *
     * @param productName 찾고자하는 상품 이름
     * @param pageable
     * @return Page<SearchProductResponseDto>
     **/
    Page<SearchProductResponseDto> searchProductByProductName(Pageable pageable, String productName);


}
