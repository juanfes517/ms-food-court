package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.IOrderServicePort;
import com.pragma.foodcourt.domain.exception.CustomerHasActiveOrderException;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.domain.spi.IJwtSecurityServicePort;
import com.pragma.foodcourt.domain.spi.IOrderPersistencePort;
import com.pragma.foodcourt.domain.spi.IUserExternalServicePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IUserExternalServicePort userExternalServicePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IJwtSecurityServicePort jwtSecurityServicePort;

    @Override
    public Order placeOrder(Order order) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long customerId = userExternalServicePort.getUserIdByEmail(tokenEmail);

        List<Order> customerOrders = orderPersistencePort.findAllByCustomerId(customerId).stream()
                .filter(customerOrder -> customerOrder.getStatus() == OrderStatusEnum.PREPARING
                        || customerOrder.getStatus() == OrderStatusEnum.PENDING
                        || customerOrder.getStatus() == OrderStatusEnum.READY)
                .toList();

        if (!customerOrders.isEmpty()) {
            throw new CustomerHasActiveOrderException(ExceptionConstants.CUSTOMER_HAS_ACTIVE_ORDER_EXCEPTION);
        }

        order.setCustomerId(customerId);

        return orderPersistencePort.save(order);
    }
}
