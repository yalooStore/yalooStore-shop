package com.yaloostore.shop.cart.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 장바구니에 담긴 상품 정보를 보여주는 dto 객체입니다.
 * */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCartDto {

    private Long productId;
    private Long quantity;
    private String thumbnailUrl;
    private String productName;
    private Long discountPrice;
    private Long rawPrice;
    private Long discountPercent;
    private Long savedPoint;


    private Boolean forcedOutOfStock;
    private Boolean isSale;
    private Boolean isDeleted;
    private Boolean isEbook;



}
