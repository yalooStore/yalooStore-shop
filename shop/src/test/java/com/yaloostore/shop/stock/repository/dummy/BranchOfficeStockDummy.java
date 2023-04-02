package com.yaloostore.shop.stock.repository.dummy;

import com.yaloostore.shop.product.entity.Product;
import com.yaloostore.shop.product.repository.dummy.ProductDummy;
import com.yaloostore.shop.stock.entity.BranchOfficeStock;

public class BranchOfficeStockDummy {

    public static BranchOfficeStock dummy(Product product){

        return BranchOfficeStock.builder()
                .product(product)
                .quantity(100L)
                .build();

    }
}
