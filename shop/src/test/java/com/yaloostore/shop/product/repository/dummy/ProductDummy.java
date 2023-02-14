package com.yaloostore.shop.product.repository.dummy;

import com.yaloostore.shop.book.Book;
import com.yaloostore.shop.product.entity.Product;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProductDummy {

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
                .build();
    }
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
                .discountPercent(0L)
                .build();
    }




}
