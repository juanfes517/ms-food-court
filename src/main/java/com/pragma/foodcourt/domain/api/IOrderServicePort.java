package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;

import java.util.List;

public interface IOrderServicePort {

    Order placeOrder(Order order);

    List<Order> getAllOrders(int page, int pageSize, OrderStatusEnum status);

    Order assignOrder(Long orderId);

    int markOrderReady(Long orderId);

    Order markOrderDelivered(Long orderId, String securityPin);
}
