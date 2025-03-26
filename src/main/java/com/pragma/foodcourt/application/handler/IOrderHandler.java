package com.pragma.foodcourt.application.handler;

import com.pragma.foodcourt.application.dto.request.OrderRequestDto;
import com.pragma.foodcourt.application.dto.response.OrderResponseDto;
import com.pragma.foodcourt.application.dto.response.OrderWithDishesResponseDto;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;

import java.util.List;

public interface IOrderHandler {

    OrderWithDishesResponseDto placeOrder(OrderRequestDto orderRequest);

    List<OrderResponseDto> getAllOrders(int page, int pageSize, OrderStatusEnum status);

    OrderResponseDto assignOrder(Long orderId);
}
