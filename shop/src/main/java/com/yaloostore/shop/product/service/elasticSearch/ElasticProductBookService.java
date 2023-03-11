package com.yaloostore.shop.product.service.elasticSearch;

import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.SearchProductBookResponseDto;

import org.springframework.data.domain.Pageable;

public interface ElasticProductBookService {


    /**
     * 엘라스틱 서치를 사용해서 isbn으로 해당하는 검색 정보를 찾아오는 메소드입니다.
     * */
    PaginationResponseDto<SearchProductBookResponseDto> searchProductBookByIsbn(Pageable pageable, String isbn);


}
