package com.yaloostore.shop.product.controller;

import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.product.dto.response.ProductTypeResponseDto;
import com.yaloostore.shop.product.service.inter.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/service/product-types")
@RequiredArgsConstructor
public class ProductTypeRestController {

    private final ProductTypeService productTypeService;


    /**
     * 상품의 유형을 모두 조회합니다.
     *
     * @return 전체 조회한 상품 유형의 dto 객체
     * */
    @GetMapping
    public ResponseDto<List<ProductTypeResponseDto>> getProductTypes(){

        return ResponseDto
                .<List<ProductTypeResponseDto>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(productTypeService.findAllProductTypes())
                .build();
    }

}
