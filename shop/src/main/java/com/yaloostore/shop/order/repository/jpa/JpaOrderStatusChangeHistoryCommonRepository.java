package com.yaloostore.shop.order.repository.jpa;

import com.yaloostore.shop.order.entity.OrderStatusChangeHistory;
import com.yaloostore.shop.order.entity.OrderStatusChangeHistory.OrderStatusChangeHistoryPk;
import com.yaloostore.shop.order.repository.common.CommonOrderStatusChangeHistory;
import org.springframework.data.repository.Repository;

public interface JpaOrderStatusChangeHistoryCommonRepository extends Repository<OrderStatusChangeHistory, OrderStatusChangeHistoryPk>, CommonOrderStatusChangeHistory {
}
