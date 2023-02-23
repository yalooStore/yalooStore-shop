package com.yaloostore.shop.order.common;


import com.yalooStore.common_utils.code.ErrorCode;
import com.yalooStore.common_utils.exception.ClientException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum OrderStatusCode {


    ORDER(1),
    DEPOSIT(2),
    READY(3),
    DELIVERY(4),
    COMPLETE(5),
    CONFIRM(6),
    REFUND(7),
    CANCEL(8)
    ;
    private final int orderStatus;

    /**
     * 문자로 입력받은 주문 상태를 이용해 주문상태 코드를 찾아주는 메소드입니다.
     *
     * @param orderStatus 주문상태
     * @return 주문상태 코드
     * */
    public static OrderStatusCode findByOrderStatusByString(String orderStatus){
        return Arrays.stream(OrderStatusCode.values())
                .filter(code -> orderStatus.equals(code.name()))
                .findFirst().orElseThrow(()-> new ClientException(ErrorCode.BAD_REQUEST, ErrorCode.BAD_REQUEST.getName()));
    }


    /**
     * 상태코드로 주문상태 코드를 찾는 메소드입니다.
     *
     * @param orderStatus 상태
     * @return 주문 상태코드
     * */
    public static OrderStatusCode findByOrderStatusCodeByNumber(Long orderStatus){
        return Arrays.stream(OrderStatusCode.values())
                .filter(code -> code.getOrderStatus() == orderStatus)
                .findFirst()
                .orElseThrow(()->new ClientException(
                        ErrorCode.BAD_REQUEST, ErrorCode.BAD_REQUEST.getName()));
    }

}
