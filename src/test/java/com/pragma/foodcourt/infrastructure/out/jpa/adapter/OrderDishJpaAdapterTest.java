package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderDish;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.OrderDishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDishJpaAdapterTest {

    @InjectMocks
    private OrderDishJpaAdapter orderDishJpaAdapter;

    @Mock
    private OrderDishRepository orderDishRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void save_WhenIsSuccessful() {

        Order order = new Order();
        Dish dish = new Dish();
        OrderEntity orderEntity = new OrderEntity();
        DishEntity dishEntity = new DishEntity();

        OrderDish orderDish = OrderDish.builder()
                .id(null)
                .order(order)
                .dish(dish)
                .quantity(2)
                .build();

        OrderDishEntity mappedOrderDishEntity = OrderDishEntity.builder()
                .id(null)
                .order(orderEntity)
                .dish(dishEntity)
                .quantity(2)
                .build();

        OrderDishEntity savedOrderDishEntity = OrderDishEntity.builder()
                .id(1L)
                .order(orderEntity)
                .dish(dishEntity)
                .quantity(2)
                .build();

        OrderDish savedOrderDish = OrderDish.builder()
                .id(1L)
                .order(order)
                .dish(dish)
                .quantity(2)
                .build();

        when(modelMapper.map(orderDish, OrderDishEntity.class))
                .thenReturn(mappedOrderDishEntity);
        when(orderDishRepository.save(mappedOrderDishEntity))
                .thenReturn(savedOrderDishEntity);
        when(modelMapper.map(savedOrderDishEntity, OrderDish.class))
                .thenReturn(savedOrderDish);

        OrderDish result = orderDishJpaAdapter.save(orderDish);

        assertNotNull(result);
        assertEquals(savedOrderDish.getId(), result.getId());
        assertEquals(savedOrderDish.getOrder(), result.getOrder());
        assertEquals(savedOrderDish.getDish(), result.getDish());
        assertEquals(savedOrderDish.getQuantity(), result.getQuantity());
    }
}