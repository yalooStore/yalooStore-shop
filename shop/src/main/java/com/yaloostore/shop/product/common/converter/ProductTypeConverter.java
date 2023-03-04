package com.yaloostore.shop.product.common.converter;


import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.product.common.ProductTypeCode;
import jakarta.persistence.AttributeConverter;

import jakarta.persistence.Converter;

import java.util.Arrays;


@Converter
public class ProductTypeConverter implements AttributeConverter<ProductTypeCode, Integer> {


    @Override
    public Integer convertToDatabaseColumn(ProductTypeCode productTypeCode) {
        return productTypeCode.getTypeId();
    }

    @Override
    public ProductTypeCode convertToEntityAttribute(Integer integer) {
        return Arrays.stream(ProductTypeCode.values())
                .filter(code -> integer.equals(code.getTypeId()))
                .findAny()
                .orElseThrow(()-> new ClientException(ErrorCode.PRODUCT_NOT_FOUND,
                        "this product type is not found"));

    }
}
