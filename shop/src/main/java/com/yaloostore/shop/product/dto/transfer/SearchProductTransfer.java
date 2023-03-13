package com.yaloostore.shop.product.dto.transfer;


import com.yaloostore.shop.product.dto.response.SearchProductResponseDto;
import com.yaloostore.shop.product.documents.SearchProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductTransfer {


    private Long productId;

    private String productName;

    private Long stock;

    private LocalDateTime productCreatedAt;

    private String description;

    private String thumbnailUrl;

    private Long fixedPrice;

    private Long rawPrice;

    private Boolean isSelled;

    private Boolean isDeleted;

    private Boolean isExpose;

    private Long discountPercent;


    /**
     * dto 객체를 엘라스틱 서치 index로 변환해주는 메소드입니다.
     * */
    public static SearchProductResponseDto fromEntity(SearchProduct searchProduct){
        return SearchProductResponseDto.builder()
                .productId(searchProduct.getProductId())
                .productName(searchProduct.getProductName())
                .stock(searchProduct.getStock())
                .productCreatedAt(searchProduct.getProductCreatedAt())
                .description(searchProduct.getDescription())
                .thumbnailUrl(searchProduct.getThumbnailUrl())
                .fixedPrice(searchProduct.getFixedPrice())
                .rawPrice(searchProduct.getRawPrice())
                .isSelled(searchProduct.getIsSelled())
                .isDeleted(searchProduct.getIsDeleted())
                .isExpose(searchProduct.getIsExpose())
                .discountPercent(searchProduct.getDiscountPercent())
                .build();
    }


    /**
     * 엘라스틱 index를 dto로 변환해주는 메소드입니다.
     * */
    public SearchProduct toEntity(){
        return SearchProduct.builder()
                .productId(this.productId)
                .productName(this.productName)
                .stock(this.stock)
                .productCreatedAt(this.productCreatedAt.toLocalDate())
                .description(this.description)
                .thumbnailUrl(this.thumbnailUrl)
                .fixedPrice(this.fixedPrice)
                .rawPrice(this.rawPrice)
                .isSelled(this.isSelled)
                .isDeleted(this.isDeleted)
                .isExpose(this.isExpose)
                .discountPercent(this.discountPercent)
                .build();
    }

}
