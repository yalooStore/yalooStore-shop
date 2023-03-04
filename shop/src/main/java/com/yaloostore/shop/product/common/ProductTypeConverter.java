package com.yaloostore.shop.product.common;


import ch.qos.logback.core.net.server.Client;
import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import com.yaloostore.shop.product.entity.ProductType;
import jakarta.persistence.AttributeConverter;

import jakarta.persistence.Converter;

import java.util.Arrays;


@Converter
public class ProductTypeConverter implements AttributeConverter<ProductType, Integer> {


    @Override
    public Integer convertToDatabaseColumn(ProductType productType) {
        return productType.getTypeId();
    }

    @Override
    public ProductType convertToEntityAttribute(Integer integer) {
        return Arrays.stream(ProductType.values())
                .filter(code -> integer.equals(code.getTypeId()))
                .findAny()
                .orElseThrow(()-> new ClientException(ErrorCode.PRODUCT_NOT_FOUND,
                        "this product type is not found"));

    }
}
