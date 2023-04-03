package com.yaloostore.shop.product.dto.response;

import com.yaloostore.shop.product.entity.Product;
import lombok.*;


/**
 * 상품 상세페이지를 보여줄 때 사용하는 dto 객체입니다.
 * */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductDetailViewResponse {

    private Long productId;
    private String productName;
    private String thumbnail;
    private Long rawPrice;
    private Long discountPrice;
    private Long discountPercent;
    private Boolean isSold;
    private Long quantity;
    private String description;

    //book
    private String isbn;
    private Long pageCnt;
    private String publisherName;
    private String authorName;

    public static ProductDetailViewResponse fromEntity(Product product){
        return new ProductDetailViewResponse(
                product.getProductId(),
                product.getProductName(),
                product.getThumbnailUrl(),
                product.getRawPrice(),
                product.getDiscountPrice(),
                product.getDiscountPercent(),
                product.getIsSold(),
                product.getStock(),
                product.getDescription(),
                product.getBook().getIsbn(),
                product.getBook().getPageCount(),
                product.getBook().getPublisherName(),
                product.getBook().getAuthorName()
        );

    }

}
