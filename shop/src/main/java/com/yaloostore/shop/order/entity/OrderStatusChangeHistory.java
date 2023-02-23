package com.yaloostore.shop.order.entity;


import com.yaloostore.shop.order.common.OrderStatusCode;
import com.yaloostore.shop.order.common.converter.OrderStatusCodeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 주문 상태 변경 이력을 저장하기 위한 엔티티입니다.
 * */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_status_change_history")
public class OrderStatusChangeHistory {


    @EmbeddedId
    private OrderStatusChangeHistoryPk orderStatusChangeHistoryPk;


    @Convert(converter = OrderStatusCodeConverter.class)
    @Column(name = "order_status_type")
    private OrderStatusCode orderStatusCode;


    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;


    /**
     * 해당 주문에 관련된 키와, 주문 변경 시간을 복합키로 사용해서 주키로 사용하고 있습니다.
     * */
    public static OrderStatusChangeHistory create(Order order,LocalDateTime changeTime,OrderStatusCode orderStatusCode){
        OrderStatusChangeHistoryPk pk = new OrderStatusChangeHistoryPk(order.getOrderId(), changeTime);

        return new OrderStatusChangeHistory(pk, orderStatusCode, order);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class OrderStatusChangeHistoryPk implements Serializable {
        @Column(name = "order_id", nullable = false)
        private Long orderId;

        @Column(name = "order_status_change_date_time", nullable = false)
        private LocalDateTime OrderStatusChangeDateTime;
    }

}
