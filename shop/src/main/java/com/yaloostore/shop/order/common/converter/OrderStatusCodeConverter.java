package com.yaloostore.shop.order.common.converter;


import com.yaloostore.shop.order.common.OrderStatusCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class OrderStatusCodeConverter implements AttributeConverter<OrderStatusCode, Integer> {


    /**
     * 주문 상태 코드를 Integer 타입으로 변환해주는 메소드입니다.
     *
     * @param orderStatusCode 주문상태코드
     * @return 주문 상태코드
     *
     * */
    @Override
    public Integer convertToDatabaseColumn(OrderStatusCode orderStatusCode) {
        return orderStatusCode.getOrderStatus();
    }

    /**
     * 주문 상태 코드를 OrderStatusCode 타입으로 변환(integer -> OrderStatusCode) 메소드입니다.
     * */
    @Override
    public OrderStatusCode convertToEntityAttribute(Integer integer) {
        return Arrays.stream(OrderStatusCode.values())
                .filter(c -> integer.equals(c.getOrderStatus()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
