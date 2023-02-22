package com.yaloostore.shop.order.entity;


import com.yaloostore.shop.order.common.OrderCode;
import com.yaloostore.shop.order.common.converter.OrderCodeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


/**
 * 상속을 사용한 고급매핑 방식으로 join 전략을 사용해서 진행
 * 데이터를 가져올 땐, 조인을 사용해서 데이터를 가져오게 된다.
 *  */
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Order {


    @Column(name = "is_hidden", nullable = false)
    protected boolean isHidden;

    @Id
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "order_name", nullable = false)
    private String orderName;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Column(name = "expected_transport_date")
    private LocalDateTime expectedTransportDate;

    @Column(name = "used_point", nullable = false)
    private Long usedPoint;

    @Column(name = "saved_point", nullable = false)
    private Long savedPoint;

    @Column(name = "shipping_fee", nullable = false)
    private int shippingFee;

    @Column(name = "gift_wrapping_fee", nullable = false)
    private int giftWrappingFee;


    @Column(name = "total_amount",nullable = false)
    private Long totalAmount;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "recipient_phone_number",nullable = false)
    private String recipientPhoneNumber;

    @Column(name = "order_code_id", nullable = false)
    @Converter(converter = OrderCodeConverter.class)
    private OrderCode orderCode;






}
