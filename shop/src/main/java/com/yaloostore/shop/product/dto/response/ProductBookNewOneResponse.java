package com.yaloostore.shop.product.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쇼핑몰에 새로 등록된 상품을 위한 dto 객체(책)
 * */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBookNewOneResponse {
    private String productName;
    private String description;
    private String thumbnailUrl;
    private Long fixedPrice;
    private Long rawPrice;
    private Long discountPercent;
    private String isbn;
    private String publisherName;
    private String authorName;


}
