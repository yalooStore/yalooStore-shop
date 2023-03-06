package com.yaloostore.shop.product.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductTypeResponseDto {

    private Integer typeId;
    private String typeName;
    private String typeKoName;
}
