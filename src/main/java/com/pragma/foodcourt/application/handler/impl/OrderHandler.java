package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.DishRequestDto;
import com.pragma.foodcourt.application.dto.request.OrderRequestDto;
import com.pragma.foodcourt.application.dto.response.DishOrderResponseDto;
import com.pragma.foodcourt.application.dto.response.OrderResponseDto;
import com.pragma.foodcourt.application.dto.response.OrderWithDishesResponseDto;
import com.pragma.foodcourt.application.handler.IOrderHandler;
import com.pragma.foodcourt.domain.api.IOrderDishServicePort;
import com.pragma.foodcourt.domain.api.IOrderServicePort;
import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderDish;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {

    private final IOrderServicePort orderServicePort;
    private final IOrderDishServicePort orderDishServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final ModelMapper modelMapper;

    @Override
    public OrderWithDishesResponseDto placeOrder(OrderRequestDto orderRequest) {
        Restaurant restaurant = restaurantServicePort.findRestaurantById(orderRequest.getRestaurantId());
        Order order = Order.builder()
                .date(LocalDate.now())
                .status(OrderStatusEnum.PENDING)
                .restaurantId(restaurant.getId())
                .build();

        Order savedOrder = orderServicePort.placeOrder(order);
        List<DishOrderResponseDto> savedDishes = this.saveDishOrders(orderRequest.getDishes(), savedOrder);

        return OrderWithDishesResponseDto.builder()
                .id(savedOrder.getId())
                .date(savedOrder.getDate())
                .status(savedOrder.getStatus())
                .dishes(savedDishes)
                .build();
    }

    @Override
    public List<OrderResponseDto> getAllOrders(int page, int pageSize, OrderStatusEnum status) {
        List<Order> orders = orderServicePort.getAllOrders(page, pageSize, status);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDto.class))
                .toList();
    }

    @Override
    public OrderResponseDto assignOrder(Long orderId) {
        Order order = orderServicePort.assignOrder(orderId);
        return modelMapper.map(order, OrderResponseDto.class);
    }

    private List<DishOrderResponseDto> saveDishOrders(List<DishRequestDto> dishes, Order savedOrder) {
        List<OrderDish> orderDishes = new ArrayList<>();
        dishes.forEach(dish -> {
            OrderDish orderDish = orderDishServicePort.save(savedOrder, dish.getQuantity(), dish.getDishId());
            orderDishes.add(orderDish);
        });

        return orderDishes.stream()
                .map(dish -> {
                    DishOrderResponseDto dishDto = modelMapper.map(dish.getDish(), DishOrderResponseDto.class);
                    dishDto.setQuantity(dish.getQuantity());
                    return dishDto;
                })
                .toList();
    }
}
