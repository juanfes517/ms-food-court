package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.IOrderDishServicePort;
import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderDish;
import com.pragma.foodcourt.domain.spi.IDishPersistencePort;
import com.pragma.foodcourt.domain.spi.IOrderDishPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderDishUseCase implements IOrderDishServicePort {

    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IDishPersistencePort dishPersistencePort;

    @Override
    public OrderDish save(Order order, int quantity, Long dishId) {
        Dish dish = dishPersistencePort.findById(dishId);
        OrderDish orderDish = OrderDish.builder()
                .order(order)
                .dish(dish)
                .quantity(quantity)
                .build();

        return orderDishPersistencePort.save(orderDish);

    }
}
