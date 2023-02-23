package com.yaloostore.shop.order.repository.common;

import com.yaloostore.shop.order.entity.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Optional;


/**
 * @param <T> 주문 상속 타입
 *
 * Order를 부모로 나머지를 상속받아서 진행했기 때문에 이와 같이 진행해주어야 한다. (고급매핑-상속)
 * */
public interface CommonOrderRepository<T extends Order> {

    Order save(Order order);

    Optional<Order> findById(Long orderId);

    void deleteById(Long orderId);

    List<Order> findAll();


}
