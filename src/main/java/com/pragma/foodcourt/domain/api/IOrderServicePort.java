package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.Order;

public interface IOrderServicePort {

    Order placeOrder(Order order);
}
