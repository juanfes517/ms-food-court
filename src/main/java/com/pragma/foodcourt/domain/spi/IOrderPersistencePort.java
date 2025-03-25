package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {

    Order save(Order order);

    List<Order> findAllByCustomerId(Long customerId);
}
