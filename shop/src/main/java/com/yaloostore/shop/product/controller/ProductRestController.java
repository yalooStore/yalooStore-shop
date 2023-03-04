package com.yaloostore.shop.product.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.common.dto.PaginationResponseDto;
import com.yaloostore.shop.product.dto.response.ProductBookNewOneResponse;
import com.yaloostore.shop.product.dto.response.ProductBookResponseDto;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/service/products")
@RequiredArgsConstructor
public class ProductRestController {


    private final QueryProductService queryProductService;


    @GetMapping("/new/stock/book")
    public ResponseEntity<List<ProductBookNewOneResponse>> getNewOneLists(){

        List<ProductBookNewOneResponse> response = queryProductService.getProductBookByNewOne();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/books")
    public ResponseDto<PaginationResponseDto<ProductBookResponseDto>> getProducts(Pageable pageable,
                                                                                  @RequestParam(required = false) Integer typeId) {

        return ResponseDto.<PaginationResponseDto<ProductBookResponseDto>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(queryProductService.getAllByProductBookList(pageable, typeId))
                .build();
    }
}
