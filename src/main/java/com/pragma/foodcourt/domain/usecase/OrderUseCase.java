package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.IOrderServicePort;
import com.pragma.foodcourt.domain.exception.CustomerHasActiveOrderException;
import com.pragma.foodcourt.domain.exception.InvalidOrderStatusException;
import com.pragma.foodcourt.domain.exception.OrderNotFromEmployeeRestaurantException;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.domain.spi.IEmployeeAssignmentPersistencePort;
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
    private final IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort;

    @Override
    public Order placeOrder(Order order) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long customerId = userExternalServicePort.getUserIdByEmail(tokenEmail);

        this.validateActiveOrder(customerId);
        order.setCustomerId(customerId);

        return orderPersistencePort.save(order);
    }

    @Override
    public List<Order> getAllOrders(int page, int pageSize, OrderStatusEnum status) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long employeeId = userExternalServicePort.getUserIdByEmail(tokenEmail);
        Long restaurantId = this.findEmployeeRestaurantId(employeeId);

        return orderPersistencePort.findAll(page, pageSize, status, restaurantId);
    }

    @Override
    public Order assignOrder(Long orderId) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long employeeId = userExternalServicePort.getUserIdByEmail(tokenEmail);
        Order order = orderPersistencePort.findById(orderId);

        this.validateOrderRestaurant(order, employeeId);
        this.validateOrderStatus(order, OrderStatusEnum.PENDING);

        order.setChefId(employeeId);
        order.setStatus(OrderStatusEnum.PREPARING);

        return orderPersistencePort.save(order);
    }

    private void validateOrderStatus(Order order, OrderStatusEnum expectedStatus) {
        OrderStatusEnum actualStatus = order.getStatus();
        if (actualStatus != expectedStatus) {
            throw new InvalidOrderStatusException(ExceptionConstants.INVALID_ORDER_STATUS_EXCEPTION);
        }
    }

    private void validateOrderRestaurant(Order order, Long employeeId) {
        Long employeeRestaurantId = this.findEmployeeRestaurantId(employeeId);
        Long orderRestaurantId = order.getRestaurantId();
        if (!orderRestaurantId.equals(employeeRestaurantId)) {
            throw new OrderNotFromEmployeeRestaurantException(ExceptionConstants.ORDER_NOT_FROM_EMPLOYEE_RESTAURANT_EXCEPTION);
        }
    }

    private Long findEmployeeRestaurantId(Long employeeId) {
        EmployeeAssignment employeeAssignment = employeeAssignmentPersistencePort.findByEmployeeId(employeeId);
        return employeeAssignment.getRestaurant().getId();
    }

    private void validateActiveOrder(Long customerId) {
        List<Order> customerOrders = orderPersistencePort.findAllByCustomerId(customerId).stream()
                .filter(customerOrder -> customerOrder.getStatus() == OrderStatusEnum.PREPARING
                        || customerOrder.getStatus() == OrderStatusEnum.PENDING
                        || customerOrder.getStatus() == OrderStatusEnum.READY)
                .toList();

        if (!customerOrders.isEmpty()) {
            throw new CustomerHasActiveOrderException(ExceptionConstants.CUSTOMER_HAS_ACTIVE_ORDER_EXCEPTION);
        }
    }
}
