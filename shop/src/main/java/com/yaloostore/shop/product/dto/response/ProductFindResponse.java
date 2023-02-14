package com.yaloostore.shop.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductFindResponse {

    //product 정보
    private String productName;
    private Long stock;
    private String description;
    private Long fixedPrice;
    private Long rawPrice;
    private Long discountPercent;

    //book 정보 (query dsl로 조인해서 가져올 것)
    private String isbn;
    private Long pageCount;
    private LocalDateTime bookCreatedAt;
    private Boolean isEbook;
    private String ebookUrl;
    private String publisherName;
    private String authorName;
}
