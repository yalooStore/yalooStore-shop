package com.yaloostore.shop.product.dto.response;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
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
