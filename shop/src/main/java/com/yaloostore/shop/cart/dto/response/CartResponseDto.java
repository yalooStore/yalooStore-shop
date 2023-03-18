package com.yaloostore.shop.cart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDto {
    //product
    private Long productId;
    private String productName;
    private String thumbnailUrl;
    private Long rawPrice;

}
