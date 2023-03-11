package com.yaloostore.shop.product.controller.elasticSearch;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.SearchProductResponseDto;
import com.yaloostore.shop.product.service.elasticSearch.ElasticProductService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 엘라스틱 서치를 사용해서 상품 검색을 하는 컨트롤러입니다.
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service/products/search")
public class ElasticProductRestController {

    private final ElasticProductService elasticProductService;



    @GetMapping(params = "productName")
    public ResponseDto<PaginationResponseDto<SearchProductResponseDto>> searchProductByProductNamePagination(@RequestParam @Size(max = 30) String productName,
                                                                                                   @PageableDefault Pageable pageable){

        PaginationResponseDto<SearchProductResponseDto> response = elasticProductService.searchProductByProductNamePagination(pageable, productName);

        return ResponseDto.<PaginationResponseDto<SearchProductResponseDto>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(response)
                .build();

    }



}
