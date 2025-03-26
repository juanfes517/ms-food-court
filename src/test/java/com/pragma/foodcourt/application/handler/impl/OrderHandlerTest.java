package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.DishRequestDto;
import com.pragma.foodcourt.application.dto.request.OrderRequestDto;
import com.pragma.foodcourt.application.dto.response.CategoryResponseDto;
import com.pragma.foodcourt.application.dto.response.DishOrderResponseDto;
import com.pragma.foodcourt.application.dto.response.OrderResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantResponseDto;
import com.pragma.foodcourt.domain.api.IOrderDishServicePort;
import com.pragma.foodcourt.domain.api.IOrderServicePort;
import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderHandlerTest {

    @InjectMocks
    private OrderHandler orderHandler;

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IOrderDishServicePort orderDishServicePort;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void placeOrder_WhenIsSuccessful() {
        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .restaurantId(1L)
                .dishes(List.of(new DishRequestDto(1L, 2)))
                .build();

        Order savedOrder = Order.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.now())
                .status(OrderStatusEnum.PENDING)
                .restaurantId(orderRequestDto.getRestaurantId())
                .build();

        Dish dish1 = Dish.builder()
                .id(orderRequestDto.getDishes().get(0).getDishId())
                .name("Dish 1")
                .category(new Category())
                .description("Dish description 1")
                .price(34)
                .restaurant(new Restaurant())
                .imageUrl("imageUrl")
                .build();

        OrderDish savedOrderDish = OrderDish.builder()
                .id(1L)
                .order(savedOrder)
                .dish(dish1)
                .quantity(orderRequestDto.getDishes().get(0).getQuantity())
                .build();

        DishOrderResponseDto dishOrderResponse1 = DishOrderResponseDto.builder()
                .id(orderRequestDto.getDishes().get(0).getDishId())
                .name("Dish 1")
                .category(new CategoryResponseDto())
                .description("Dish description 1")
                .price(34)
                .restaurant(new RestaurantResponseDto())
                .imageUrl("imageUrl")
                .quantity(orderRequestDto.getDishes().get(0).getQuantity())
                .build();

        List<DishOrderResponseDto> savedDishes = List.of(dishOrderResponse1);

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .id(savedOrder.getId())
                .date(savedOrder.getDate())
                .status(savedOrder.getStatus())
                .dishes(savedDishes)
                .build();

        when(orderServicePort.placeOrder(any(Order.class)))
                .thenReturn(savedOrder);
        when(restaurantServicePort.findRestaurantById(orderRequestDto.getRestaurantId()))
                .thenReturn(new Restaurant());
        when(orderDishServicePort
                .save(savedOrder, orderRequestDto.getDishes().get(0).getQuantity(), orderRequestDto.getDishes().get(0).getDishId()))
                .thenReturn(savedOrderDish);
        when(modelMapper.map(savedOrderDish.getDish(), DishOrderResponseDto.class))
                .thenReturn(dishOrderResponse1);

        OrderResponseDto result = orderHandler.placeOrder(orderRequestDto);

        assertNotNull(result);
        assertEquals(orderResponseDto.getId(), result.getId());
        assertEquals(orderResponseDto.getDate(), result.getDate());
        assertEquals(orderResponseDto.getStatus(), result.getStatus());
        assertEquals(orderResponseDto.getDishes().size(), result.getDishes().size());
    }
}
