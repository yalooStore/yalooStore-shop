package com.yaloostore.shop.book.dummy;


import com.yaloostore.shop.book.entity.Book;
import com.yaloostore.shop.product.entity.Product;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookDummy {


    public static Book dummy(Product product){
        return Book.builder()
                .productId(product.getProductId())
                .product(product)
                .isbn("test isbn")
                .pageCount(300L)
                .bookCreatedAt(LocalDate.now().minusDays(7))
                .isEbook(true)
                .ebookUrl("test url")
                .publisherName("민음사")
                .authorName("김땡떙")
                .build();

    }
    public static Book dummy2(Product product){
        return Book.builder()
                .productId(product.getProductId())
                .product(product)
                .isbn("2")
                .pageCount(300L)
                .bookCreatedAt(LocalDate.now())
                .isEbook(true)
                .ebookUrl("2")
                .publisherName("2")
                .authorName("이땡땡")
                .build();

    }
    public static Book dummy3(Product product){
        return Book.builder()
                .productId(product.getProductId())
                .product(product)
                .isbn("3")
                .pageCount(300L)
                .bookCreatedAt(LocalDate.now().minusDays(10))
                .isEbook(true)
                .ebookUrl("3")
                .publisherName("3")
                .authorName("이dd")
                .build();

    }

}
