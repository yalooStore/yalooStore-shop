package com.yaloostore.shop.product.repository.dummy;

import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.product.common.ProductTypeCode;
import com.yaloostore.shop.product.entity.Product;

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
                .discountPrice(1000L)
                .rawPrice(100L)
                .isSold(false)
                .isDeleted(false)
                .isExpose(true)
                .discountPercent(0L)
                .productTypeCode(ProductTypeCode.NONE)
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
                .discountPrice(2000L)
                .rawPrice(200L)
                .isSold(false)
                .isDeleted(false)
                .isExpose(false)
                .productTypeCode(ProductTypeCode.BESTSELLER)
                .discountPercent(0L)
                .build();
    }
         public static Product dummyNonProductType(){
                return Product.builder()
                        .productName("dummy2")
                        .stock(2L)
                        .productCreatedAt(LocalDateTime.now())
                        .description("test2")
                        .thumbnailUrl("thumbnailUrl2")
                        .discountPrice(2000L)
                        .rawPrice(200L)
                        .isSold(false)
                        .isDeleted(false)
                        .isExpose(false)
                        .discountPercent(0L)
                        .build();
            }
        public static Product dummy(Book book){
            return Product.builder()
                .productName("dummy product")
                .stock(0L)
                .productCreatedAt(LocalDateTime.now())
                .description("test")
                .thumbnailUrl("thumbnailUrl")
                .discountPrice(1000L)
                .rawPrice(100L)
                .isSold(false)
                .isDeleted(false)
                .isExpose(true)
                .discountPercent(0L)
                .productTypeCode(ProductTypeCode.NONE)
                .book(book)
                .build();
    }

    /**
     * bestSeller product type
     * */
    public static Product dummy2(Book book){
        return Product.builder()
                .productName("dummy2")
                .stock(2L)
                .productCreatedAt(LocalDateTime.now())
                .description("test2")
                .thumbnailUrl("thumbnailUrl2")
                .discountPrice(2000L)
                .rawPrice(200L)
                .isSold(false)
                .isDeleted(false)
                .isExpose(false)
                .productTypeCode(ProductTypeCode.BESTSELLER)
                .discountPercent(0L)
                .book(book)
                .build();
    }
         public static Product dummyNonProductType(Book book){
                return Product.builder()
                        .productName("dummy2")
                        .stock(2L)
                        .productCreatedAt(LocalDateTime.now())
                        .description("test2")
                        .thumbnailUrl("thumbnailUrl2")
                        .discountPrice(2000L)
                        .rawPrice(200L)
                        .isSold(false)
                        .isDeleted(false)
                        .isExpose(false)
                        .discountPercent(0L)
                        .book(book)
                        .build();
            }

}
