package com.yaloostore.shop.product.product_type.dummy;

import com.yaloostore.shop.product.product_type.entity.ProductType;

public class ProductTypeDummy {

    public static ProductType dummy(){
        return ProductType.builder()
                .productTypeName("book")
                .build();
    }
}
