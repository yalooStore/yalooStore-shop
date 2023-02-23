package com.yaloostore.shop.order.dto.transefer;


import com.yaloostore.shop.order.entity.Order;
import com.yaloostore.shop.order.entity.OrderProduct;
import com.yaloostore.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * OrderProduct 관련 dto -> entity 변환 &  entity -> dto 클래스입니다.
 * */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProductDto {

    private Long orderProductId;


    private int quantity;

    private boolean isCanceled;

    private Product product;

    private Order order;

    public static OrderProductDto fromEntity(OrderProduct orderProduct){
        return new OrderProductDto(
                orderProduct.getOrderProductId(),
                orderProduct.getQuantity(),
                orderProduct.isCanceled(),
                orderProduct.getProduct(),
                orderProduct.getOrder());
    }

    /**
     * OrderProduct entity -> dto 변환 해주는 메소드입니다.
     * */
    public OrderProduct toEntity(Product product, Order order){

        return OrderProduct.builder()
                .quantity(quantity)
                .isCanceled(isCanceled)
                .product(product)
                .order(order)
                .build();
    }

}
