package com.yaloostore.shop.product.repository.dummy;

import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.entity.ProductType;

import java.time.LocalDateTime;

public class ProductDummy {


    /**
     * None product type
     * */
    public static Product dummy(){
        return Product.builder()
                .productName("dummy product")
                .stock(0L)
                .productCreatedAt(LocalDateTime.now())
                .description("test")
                .thumbnailUrl("thumbnailUrl")
                .fixedPrice(1000L)
                .rawPrice(100L)
                .isSelled(false)
                .isDeleted(false)
                .isExpose(true)
                .discountPercent(0L)
                .productType(ProductType.NONE)
                .build();
    }

    /**
     * bestSeller product type
     * */
    public static Product dummy2(){
        return Product.builder()
                .productName("dummy2")
                .stock(2L)
                .productCreatedAt(LocalDateTime.now())
                .description("test2")
                .thumbnailUrl("thumbnailUrl2")
                .fixedPrice(2000L)
                .rawPrice(200L)
                .isSelled(false)
                .isDeleted(false)
                .isExpose(false)
                .productType(ProductType.BESTSELLER)
                .discountPercent(0L)
                .build();
    }




}
