package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.IOrderServicePort;
import com.pragma.foodcourt.domain.exception.*;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.*;
import com.pragma.foodcourt.domain.spi.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class OrderUseCase implements IOrderServicePort {

    private final IUserExternalServicePort userExternalServicePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IJwtSecurityServicePort jwtSecurityServicePort;
    private final IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort;
    private final ISmsExternalService smsExternalService;
    private final ITraceabilityExternalService traceabilityExternalService;

    private final Random random = new Random();

    @Override
    public Order placeOrder(Order order) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long customerId = userExternalServicePort.getUserIdByEmail(tokenEmail);

        this.validateActiveOrder(customerId);
        order.setCustomerId(customerId);

        Order savedOrder = orderPersistencePort.save(order);
        this.createTraceability(savedOrder, tokenEmail, null);

        return savedOrder;
    }

    @Override
    public List<Order> getAllOrders(int page, int pageSize, OrderStatusEnum status) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long employeeId = userExternalServicePort.getUserIdByEmail(tokenEmail);
        Long restaurantId = this.findEmployeeRestaurant(employeeId).getId();

        return orderPersistencePort.findAll(page, pageSize, status, restaurantId);
    }

    @Override
    public Order assignOrder(Long orderId) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long employeeId = userExternalServicePort.getUserIdByEmail(tokenEmail);
        Order order = orderPersistencePort.findById(orderId);

        this.validateOrderRestaurant(order, employeeId);
        this.validateOrderStatus(order, OrderStatusEnum.PENDING, ExceptionConstants.PENDING_STATUS_EXCEPTION);

        order.setChefId(employeeId);
        order.setStatus(OrderStatusEnum.PREPARING);

        this.createTraceability(order, employeeId, tokenEmail, OrderStatusEnum.PENDING.toString());

        return orderPersistencePort.save(order);
    }

    @Override
    public int markOrderReady(Long orderId) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long employeeId = userExternalServicePort.getUserIdByEmail(tokenEmail);
        Order order = orderPersistencePort.findById(orderId);

        Restaurant employeRestaurant = this.validateOrderRestaurant(order, employeeId);
        this.validateOrderAssignedToChef(order, employeeId);
        this.validateOrderStatus(order, OrderStatusEnum.PREPARING, ExceptionConstants.PREPARING_STATUS_EXCEPTION);

        String customerCellPhoneNumber = userExternalServicePort.getCellPhoneNumberById(order.getCustomerId());
        int securityPin = this.sendNotification(customerCellPhoneNumber, employeRestaurant.getName());

        order.setSecurityPin(String.valueOf(securityPin));
        order.setStatus(OrderStatusEnum.READY);
        orderPersistencePort.save(order);

        this.createTraceability(order, employeeId, tokenEmail, OrderStatusEnum.PREPARING.toString());

        return securityPin;
    }

    @Override
    public Order markOrderDelivered(Long orderId, String securityPin) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long employeeId = userExternalServicePort.getUserIdByEmail(tokenEmail);
        Order order = orderPersistencePort.findById(orderId);

        this.validateOrderRestaurant(order, employeeId);
        this.validateOrderAssignedToChef(order, employeeId);
        this.validateOrderStatus(order, OrderStatusEnum.READY, ExceptionConstants.READY_STATUS_EXCEPTION);
        this.validateSecurityPin(order, securityPin);

        order.setStatus(OrderStatusEnum.DELIVERED);

        this.createTraceability(order, employeeId, tokenEmail, OrderStatusEnum.READY.toString());

        return orderPersistencePort.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long customerId = userExternalServicePort.getUserIdByEmail(tokenEmail);
        Order order = orderPersistencePort.findById(orderId);

        this.validateCustomerIdOfTheOrder(order, customerId);
        this.validateOrderStatus(order, OrderStatusEnum.PENDING, ExceptionConstants.CANCEL_STATUS_EXCEPTION);

        order.setStatus(OrderStatusEnum.CANCELED);
        this.createTraceability(order, tokenEmail, OrderStatusEnum.PENDING.toString());

        return orderPersistencePort.save(order);
    }

    private void createTraceability(Order order, String customerEmail, String previousStatus) {
        CreateTraceability traceability = CreateTraceability.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .customerEmail(customerEmail)
                .previousStatus(previousStatus)
                .newStatus(order.getStatus().toString())
                .employeeId(null)
                .employeeEmail(null)
                .build();

        traceabilityExternalService.createTraceability(traceability);
    }

    private void createTraceability(Order order, Long employeeId, String employeeEmail, String previousStatus) {
        String customerEmail = userExternalServicePort.getEmailByUserId(order.getCustomerId());

        CreateTraceability traceability = CreateTraceability.builder()
                .orderId(order.getId())
                .customerId(order.getCustomerId())
                .customerEmail(customerEmail)
                .previousStatus(previousStatus)
                .newStatus(order.getStatus().toString())
                .employeeId(employeeId)
                .employeeEmail(employeeEmail)
                .build();

        traceabilityExternalService.createTraceability(traceability);
    }

    private void validateCustomerIdOfTheOrder(Order order, Long customerId) {
        Long orderCustomerId = order.getCustomerId();
        if (!orderCustomerId.equals(customerId)) {
            throw new OrderNotFromCustomerException(ExceptionConstants.ORDER_NOT_CREATED_BY_THE_CUSTOMER);
        }
    }

    private void validateSecurityPin(Order order, String securityPin) {
        String orderSecurityPin = order.getSecurityPin();
        if (!securityPin.equals(orderSecurityPin)) {
            throw new InvalidSecurityPinException(ExceptionConstants.INVALID_SECURITY_PIN_EXCEPTION);
        }
    }

    private int sendNotification(String customerCellPhoneNumber, String restaurantName) {
        int securityPin = 100000 + random.nextInt(900000);
        boolean notifyResult = smsExternalService.notifyOrderReady(customerCellPhoneNumber, restaurantName, String.valueOf(securityPin));

        if (!notifyResult) {
            throw new NotificationFailedException(ExceptionConstants.NOTIFICATION_FAILED_EXCEPTION);
        }

        return securityPin;
    }

    private void validateOrderStatus(Order order, OrderStatusEnum expectedStatus, String exceptionMessage) {
        OrderStatusEnum actualStatus = order.getStatus();
        if (actualStatus != expectedStatus) {
            throw new InvalidOrderStatusException(exceptionMessage);
        }
    }

    private void validateOrderAssignedToChef(Order order, Long employeeId) {
        Long chefId = order.getChefId();
        if (!employeeId.equals(chefId)) {
            throw new OrderNotAssignedToEmployeeException(ExceptionConstants.ORDER_NOT_ASSIGNED_TO_EMPLOYEE_EXCEPTION);
        }
    }

    private Restaurant validateOrderRestaurant(Order order, Long employeeId) {
        Restaurant employeeRestaurant = this.findEmployeeRestaurant(employeeId);
        Long employeeRestaurantId = employeeRestaurant.getId();
        Long orderRestaurantId = order.getRestaurantId();

        if (!orderRestaurantId.equals(employeeRestaurantId)) {
            throw new OrderNotFromEmployeeRestaurantException(ExceptionConstants.ORDER_NOT_FROM_EMPLOYEE_RESTAURANT_EXCEPTION);
        }

        return employeeRestaurant;
    }

    private Restaurant findEmployeeRestaurant(Long employeeId) {
        EmployeeAssignment employeeAssignment = employeeAssignmentPersistencePort.findByEmployeeId(employeeId);
        return employeeAssignment.getRestaurant();
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
