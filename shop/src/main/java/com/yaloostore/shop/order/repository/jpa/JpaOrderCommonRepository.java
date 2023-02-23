package com.yaloostore.shop.order.repository.jpa;

import com.yaloostore.shop.order.entity.Order;
import com.yaloostore.shop.order.repository.common.CommonOrderRepository;
import org.springframework.data.repository.Repository;

public interface JpaOrderCommonRepository<T extends Order> extends Repository<T, Long>, CommonOrderRepository<T> {

}
