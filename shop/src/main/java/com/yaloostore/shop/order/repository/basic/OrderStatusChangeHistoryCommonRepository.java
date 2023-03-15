package com.yaloostore.shop.order.repository.basic;

import com.yaloostore.shop.order.entity.OrderStatusChangeHistory;
import com.yaloostore.shop.order.entity.OrderStatusChangeHistory.OrderStatusChangeHistoryPk;
import com.yaloostore.shop.order.repository.common.CommonOrderStatusChangeHistory;
import org.springframework.data.repository.Repository;

public interface OrderStatusChangeHistoryCommonRepository extends Repository<OrderStatusChangeHistory, OrderStatusChangeHistoryPk>, CommonOrderStatusChangeHistory {
}
