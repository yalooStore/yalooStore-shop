package com.yaloostore.shop.product.dto.response;


import com.yaloostore.shop.product.entity.Product;
import lombok.*;


/**
 * 사용자가 최근 확인한 상품과 관련한 응답에 사용하는 response dto 입니다.
 * */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class ProductRecentResponseDto {
    private Long productId;
    private String productName;
    private String thumbnailUrl;
    private Long stock;
    //할인된 가격
    private Long discountPrice;
    //정가
    private Long rawPrice;
    private Boolean isSold; //이걸 사용해서 다팔린건 회색으로 ..
    private Boolean forcedOutOfStock;
    private Boolean isEbook;
    private String publisherName;
    private String authorName;


    /**
     * Product Entity를 ProductRecentViewResponseDto 객체로 변환시켜주는 정적 메소드입니다.
     * */
    public static ProductRecentResponseDto fromEntity(Product product){
        return ProductRecentResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .thumbnailUrl(product.getThumbnailUrl())
                .stock(product.getStock())
                .discountPrice(product.getDiscountPercent())
                .rawPrice(product.getRawPrice())
                .isSold(product.getIsSold())
                .forcedOutOfStock(product.getForcedOutOfStock())
                .isEbook(product.getBook().getIsEbook())
                .publisherName(product.getBook().getPublisherName())
                .authorName(product.getBook().getAuthorName())
                .build();

    }


}
