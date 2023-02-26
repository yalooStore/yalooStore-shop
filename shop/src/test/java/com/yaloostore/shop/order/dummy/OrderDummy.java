package com.yaloostore.shop.order.dummy;

import com.yaloostore.shop.order.common.OrderCode;
import com.yaloostore.shop.order.entity.MemberOrder;
import com.yaloostore.shop.order.entity.NonMemberOrder;
import com.yaloostore.shop.order.entity.Order;

import java.time.LocalDateTime;

public class OrderDummy {


    public static Order dummy_nonMember(){
        return NonMemberOrder.builder()
                .isHidden(false)
                .orderName("test non member name")
                .orderDateTime(LocalDateTime.now())
                .expectedTransportDate(LocalDateTime.now().plusDays(3))
                .usedPoint(0L)
                .savedPoint(1000L)
                .shippingFee(3000)
                .giftWrappingFee(0)
                .totalAmount(10000L)
                .recipientName("recipient name")
                .recipientPhoneNumber("01033334444")
                .orderCode(OrderCode.NON_MEMBER_ORDER)
                .nonMemberOrderAddress("test address")
                .nonMemberName("test non member name")
                .nonMemberPhoneNumber("01011112222")
                .build();
    }


}
