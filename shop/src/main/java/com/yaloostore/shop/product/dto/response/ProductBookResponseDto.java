package com.yaloostore.shop.product.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBookResponseDto {


    //Product
    private Long productId;
    private String productName;
    private String thumbnailUrl;
    private Long rawPrice;

    //book
    private String authorName;
    protected String publisherName;

}
