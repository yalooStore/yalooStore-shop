package com.yaloostore.shop.product.repository.dummy;

import com.yaloostore.shop.product.dto.response.ProductDetailViewResponse;
import com.yaloostore.shop.product.entity.Product;

public class ProductDetailViewResponseDummy {

    public static ProductDetailViewResponse dummy(){

        return ProductDetailViewResponse.builder()
                .productId(1L)
                .productName("test")
                .thumbnail("test")
                .rawPrice(1000L)
                .discountPrice(900L)
                .discountPercent(10L)
                .isSold(false)
                .quantity(10L)
                .description("test test")
                .isbn("1234")
                .pageCnt(300L)
                .publisherName("test publisher")
                .authorName("test author")
                .build();

    }
}
