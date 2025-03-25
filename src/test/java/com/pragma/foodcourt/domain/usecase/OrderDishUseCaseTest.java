package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.model.*;
import com.pragma.foodcourt.domain.spi.IDishPersistencePort;
import com.pragma.foodcourt.domain.spi.IOrderDishPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDishUseCaseTest {

    @InjectMocks
    private OrderDishUseCase orderDishUseCase;

    @Mock
    private IOrderDishPersistencePort orderDishPersistencePort;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Test
    void save_WhenIsSuccessful() {
        int quantity = 2;
        Long dishId = 1L;

        Order order = Order.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2025, 03, 25))
                .status(OrderStatusEnum.PENDING)
                .chefId(2L)
                .restaurantId(1L)
                .build();

        Dish dish = Dish.builder()
                .id(dishId)
                .name("Dish name")
                .category(new Category())
                .description("Dish description")
                .price(12)
                .restaurant(new Restaurant())
                .imageUrl("imageUrl")
                .active(true)
                .build();

        OrderDish savedOrderDish = OrderDish.builder()
                .id(1L)
                .order(order)
                .dish(dish)
                .quantity(quantity)
                .build();

        when(dishPersistencePort.findById(dishId))
                .thenReturn(dish);
        when(orderDishPersistencePort.save(any(OrderDish.class)))
                .thenReturn(savedOrderDish);

        OrderDish result = orderDishUseCase.save(order, quantity, dishId);

        assertNotNull(result);
        assertEquals(savedOrderDish.getId(), result.getId());
        assertEquals(savedOrderDish.getQuantity(), result.getQuantity());
        assertEquals(savedOrderDish.getDish(), result.getDish());
        assertEquals(savedOrderDish.getOrder(), result.getOrder());
    }
}