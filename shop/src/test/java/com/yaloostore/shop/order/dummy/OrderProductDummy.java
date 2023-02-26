package com.yaloostore.shop.order.dummy;


import com.yaloostore.shop.order.entity.Order;
import com.yaloostore.shop.order.entity.OrderProduct;
import com.yaloostore.shop.product.entity.Product;
import lombok.Getter;

@Getter
public class OrderProductDummy {

    public static OrderProduct dummy(Product product, Order order){
        return OrderProduct.builder()
                .quantity(10)
                .isCanceled(false)
                .product(product)
                .order(order)
                .build();
    }
}
