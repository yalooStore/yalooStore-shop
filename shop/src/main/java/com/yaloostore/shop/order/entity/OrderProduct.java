package com.yaloostore.shop.order.entity;


import com.yaloostore.shop.product.entity.Product;
import jakarta.persistence.*;
import lombok.*;


/**
 * 상품과 주문을 이어주는 엔티티로 상품이 여러개, 주문이 여러개 일수 있다.
 * */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "order_products")
@Entity
public class OrderProduct {


    @Id
    @Column(name ="order_product_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderProductId;

    @Column(nullable = false)
    private int quantity;

    
    @Column(name = "is_canceled", nullable = false)
    private boolean isCanceled;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


}
