package com.pragma.foodcourt.application.handler;

import com.pragma.foodcourt.application.dto.request.OrderRequestDto;
import com.pragma.foodcourt.application.dto.response.OrderResponseDto;

public interface IOrderHandler {

    OrderResponseDto placeOrder(OrderRequestDto orderRequest);
}
