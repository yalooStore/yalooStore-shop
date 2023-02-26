package com.yaloostore.shop.order.dto.response;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BestSellerResponse {


    private String productName;

    private String description;
    private String thumbnailUrl;
    private Long fixedPrice;

    // book
    private String isbn;
    private Long pageCount;
    private Boolean isEbook;
    private String ebookUrl;
    private String publisherName;
    private String authorName;

}
