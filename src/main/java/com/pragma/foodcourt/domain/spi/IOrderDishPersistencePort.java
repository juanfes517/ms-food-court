package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.OrderDish;

public interface IOrderDishPersistencePort {

    OrderDish save(OrderDish dish);
}
