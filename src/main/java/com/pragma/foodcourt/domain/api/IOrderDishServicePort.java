package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderDish;

public interface IOrderDishServicePort {

    OrderDish save(Order order, int quantity, Long dishId);
}
