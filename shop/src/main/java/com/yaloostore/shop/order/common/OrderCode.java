package com.yaloostore.shop.order.common;


import com.fasterxml.jackson.annotation.JsonValue;
import com.yaloostore.shop.order.entity.MemberOrder;
import com.yaloostore.shop.order.entity.NonMemberOrder;
import com.yaloostore.shop.order.entity.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * 주문시에 사용하는 주문 코드입니다.
 * */

@Getter
@RequiredArgsConstructor
public enum OrderCode {

    NON_MEMBER_ORDER(1, NonMemberOrder.class, "비회원 주문"),
    MEMBER_ORDER(2, MemberOrder.class, "회원 주문"),
    //MEMBER_SUBSCRIBE()
    ;

    private final int code;
    private final Class<? extends Order> orderClass;
    private final String koCodeName;

    /**
     * 입력받은 타입으로 주문 코드를 찾는 static method
     *
     * @param type
     * @return 주문 코드
     * */
    public static Optional<OrderCode> findByType(String type) {
        return Arrays.stream(OrderCode.values())
                .filter(code -> type.toUpperCase().equals(code.name()))
                .findFirst();
    }

    @JsonValue
    public String getKoCodeName(){
        return koCodeName;
    }


}
