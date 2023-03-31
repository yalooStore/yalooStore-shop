package com.yaloostore.shop.product.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.cart.dto.ViewCartDto;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/service/products")
@RequiredArgsConstructor
public class QueryProductRestController {

    private final QueryProductService queryProductService;


    /**
     * [GET - /api/service/products/cart] 장바구니에 나타낼 상품을 정보를 조회합니다.
     *
     * @param cart 찾고자하는 장바구니 내의 정보를 담은 Map
     * @return 장바구니 정보를 조회한 response dto 객체
     * */
    @GetMapping("/cart")
    public ResponseDto<List<ViewCartDto>> getAllCartProduct(@RequestParam Map<String, String > cart){

        List<ViewCartDto> response = queryProductService.getCartProduct(cart);


        return ResponseDto.<List<ViewCartDto>>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }





}