package com.yaloostore.shop.product.controller.elasticSearch;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.SearchProductResponseDto;
import com.yaloostore.shop.product.repository.basic.SearchProductRepository;
import com.yaloostore.shop.product.service.elasticSearch.ElasticProductService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 엘라스틱 서치를 사용해서 상품 검색을 하는 컨트롤러입니다.
 * */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service/products/search")
public class ElasticProductRestController {

    private final ElasticProductService elasticProductService;



    /**
     * [GET - /api/service/products/search/{productName}]
     * */
    @GetMapping(params = "productName")
    public ResponseDto<PaginationResponseDto<SearchProductResponseDto>> searchProductByProductNamePagination(@RequestParam @Size(max = 30) String productName,
                                                                                                   @PageableDefault Pageable pageable){

        Page<SearchProductResponseDto> page = elasticProductService.searchProductByProductName(pageable, productName);


        return ResponseDto.<PaginationResponseDto<SearchProductResponseDto>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(PaginationResponseDto.<SearchProductResponseDto>builder()
                        .dataList(page.getContent())
                        .totalDataCount(page.getTotalElements())
                        .currentPage(page.getNumber())
                        .totalPage(page.getTotalPages())
                        .build())
                .build();

    }




}
