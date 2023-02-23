package com.yaloostore.shop.order.entity;

import com.yaloostore.shop.order.common.OrderCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void findByType() {
        //given
        String type = "MEMBER_ORDER";

        //when
        Optional<OrderCode> orderCode = OrderCode.findByType(type);

        //then
        assertThat(orderCode).isPresent();
        assertThat(orderCode.get().getCode()).isEqualTo(2);
        assertThat(orderCode.get().getOrderClass()).isEqualTo(MemberOrder.class);
    }

}