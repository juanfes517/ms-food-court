package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;

import java.util.List;

public interface IOrderPersistencePort {

    Order save(Order order);

    List<Order> findAllByCustomerId(Long customerId);

    List<Order> findAll(int page, int pageSize, OrderStatusEnum status, Long restaurantId);

    Order findById(Long orderId);
}
