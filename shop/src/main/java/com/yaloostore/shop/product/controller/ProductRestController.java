package com.yaloostore.shop.product.controller;


import com.yaloostore.shop.product.dto.response.ProductBookNewOneResponse;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/service/products")
@RequiredArgsConstructor
public class ProductRestController {


    private final QueryProductService queryProductService;

    @GetMapping("/new/stock/book")
    public ResponseEntity<List<ProductBookNewOneResponse>> newOneLists(){

        List<ProductBookNewOneResponse> response = queryProductService.getProductBookByNewOne();

        return ResponseEntity.ok().body(response);
    }
}
