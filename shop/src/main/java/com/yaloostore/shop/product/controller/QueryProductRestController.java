package com.yaloostore.shop.product.controller;


import com.yalooStore.common_utils.dto.ResponseDto;
import com.yaloostore.shop.cart.dto.ViewCartDto;
import com.yaloostore.shop.product.dto.response.ProductDetailViewResponse;
import com.yaloostore.shop.product.dto.response.ProductRecentResponseDto;
import com.yaloostore.shop.product.service.inter.QueryProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/service/products")
@CrossOrigin(origins = "http://localhost:8081")
@RequiredArgsConstructor
@Slf4j
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



    /**
     * [GET - /api/service/products/{productId}] 상품의 상세 정보를 보여줍니다.
     * */
    @GetMapping("/{productId}")
    public ResponseDto<ProductDetailViewResponse> findDetailProductByProductId(@PathVariable("productId") Long productId){

        ProductDetailViewResponse response = queryProductService.getProductByProductId(productId);

        return ResponseDto.<ProductDetailViewResponse>builder()
                .success(true)
                .status(HttpStatus.OK)
                .data(response)
                .build();
    }


    /**
     * 신작 조회 메서드
     *
     * @param pageable
     * @return 신작 리스트
     * */
    @GetMapping("/new-arrivals")
    public ResponseDto<List<ProductRecentResponseDto>> getRecentProductsByBookCreated(@PageableDefault Pageable pageable){

        List<ProductRecentResponseDto> recentProducts = queryProductService.findRecentProducts(pageable);

        return ResponseDto.<List<ProductRecentResponseDto>>builder()
                .status(HttpStatus.OK)
                .success(true)
                .data(recentProducts)
                .build();

    }


}
