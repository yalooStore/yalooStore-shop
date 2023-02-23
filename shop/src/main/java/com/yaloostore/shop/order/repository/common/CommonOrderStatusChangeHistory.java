package com.yaloostore.shop.order.repository.common;

import com.yaloostore.shop.order.entity.OrderStatusChangeHistory;

public interface CommonOrderStatusChangeHistory {

    OrderStatusChangeHistory save(OrderStatusChangeHistory orderStatusChangeHistory);

}
