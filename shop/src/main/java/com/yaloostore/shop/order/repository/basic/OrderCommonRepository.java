package com.yaloostore.shop.order.repository.basic;

import com.yaloostore.shop.order.entity.Order;
import com.yaloostore.shop.order.repository.common.CommonOrderRepository;
import org.springframework.data.repository.Repository;

public interface OrderCommonRepository<T extends Order> extends Repository<T, Long>, CommonOrderRepository<T> {

}
