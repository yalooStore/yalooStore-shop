package com.yaloostore.shop.product.dto.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SearchProductBookResponseDto {

    private Long productId;

    private String productName;

    private Long stock;

    private LocalDate productCreatedAt;

    private String description;

    private String thumbnailUrl;

    private Long fixedPrice;

    private Long rawPrice;

    private Boolean isSelled;

    private Boolean isDeleted;

    private Boolean isExpose;

    private Long discountPercent;


    private String isbn;

    private Long pageCount;

    private LocalDateTime bookCreatedAt;

    private Boolean isEbook;

    private String ebookUrl;

    private String publisherName;

    private String authorName;


}
