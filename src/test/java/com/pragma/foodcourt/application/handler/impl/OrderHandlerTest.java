package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.DishRequestDto;
import com.pragma.foodcourt.application.dto.request.OrderRequestDto;
import com.pragma.foodcourt.application.dto.response.*;
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

        OrderWithDishesResponseDto orderWithDishesResponseDto = OrderWithDishesResponseDto.builder()
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

        OrderWithDishesResponseDto result = orderHandler.placeOrder(orderRequestDto);

        assertNotNull(result);
        assertEquals(orderWithDishesResponseDto.getId(), result.getId());
        assertEquals(orderWithDishesResponseDto.getDate(), result.getDate());
        assertEquals(orderWithDishesResponseDto.getStatus(), result.getStatus());
        assertEquals(orderWithDishesResponseDto.getDishes().size(), result.getDishes().size());
    }

    @Test
    void getAllOrders_WhenIsSuccessful() {
        int page = 0;
        int pageSize = 10;
        OrderStatusEnum status = OrderStatusEnum.PENDING;

        Order order = Order.builder()
                .id(1L)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .id(1L)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        List<Order> orders = List.of(order);
        List<OrderResponseDto> responseOrders = List.of(orderResponseDto);

        when(orderServicePort.getAllOrders(page, pageSize, status))
                .thenReturn(orders);
        when(modelMapper.map(order, OrderResponseDto.class))
                .thenReturn(orderResponseDto);

        List<OrderResponseDto> result = orderHandler.getAllOrders(page, pageSize, status);

        assertNotNull(result);
        assertEquals(responseOrders.size(), result.size());
        assertEquals(responseOrders.get(0).getId(), result.get(0).getId());
        assertEquals(responseOrders.get(0).getStatus(), result.get(0).getStatus());

    }

    @Test
    void assignOrder_WhenIsSuccessful() {
        Long orderId = 1L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(1L)
                .restaurantId(1L)
                .build();

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(1L)
                .restaurantId(1L)
                .build();

        when(orderServicePort.assignOrder(orderId))
                .thenReturn(order);
        when(modelMapper.map(order, OrderResponseDto.class))
                .thenReturn(orderResponseDto);

        OrderResponseDto result = orderHandler.assignOrder(orderId);

        assertNotNull(result);
        assertEquals(orderResponseDto.getId(), result.getId());
        assertEquals(orderResponseDto.getCustomerId(), result.getCustomerId());
        assertEquals(orderResponseDto.getDate(), result.getDate());
        assertEquals(orderResponseDto.getStatus(), result.getStatus());
        assertEquals(orderResponseDto.getChefId(), result.getChefId());
        assertEquals(orderResponseDto.getRestaurantId(), result.getRestaurantId());

    }

    @Test
    void markOrderReady_WhenIsSuccessful() {
        Long orderId = 1L;
        int securityPin = 333333;

        when(orderServicePort.markOrderReady(orderId))
                .thenReturn(securityPin);

        NotifyResponseDto result = orderHandler.markOrderReady(orderId);

        assertNotNull(result);
        assertEquals(securityPin, result.getSecurityPin());
        assertEquals(orderId, result.getOrderId());
    }
}
