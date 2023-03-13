package com.yaloostore.shop.product.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchProductResponseDto {

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
}
