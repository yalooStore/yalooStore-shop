package com.yaloostore.shop.order.common.converter;

import com.yaloostore.shop.order.common.OrderCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class OrderCodeConverter implements AttributeConverter<OrderCode,Integer> {


    /**
     * 주문 코드를 Integer 타입으로 변환해주는 메소드입니다.
     *
     * @param orderCode 주문코드
     * @return 주문코드의 id
     * */
    @Override
    public Integer convertToDatabaseColumn(OrderCode orderCode) {
        return orderCode.getCode();
    }



    /**
     * 주문코드를 OrderCode 타입으로 변환해주는 메소드입니다.
     *
     * @param integer 주문코드의 id
     * @return 주문코드
     * */
    @Override
    public OrderCode convertToEntityAttribute(Integer integer) {
        return Arrays.stream(OrderCode.values())
                .filter(code -> integer.equals(code.getCode()))
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }
}
