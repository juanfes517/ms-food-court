package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.exception.*;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @InjectMocks
    private OrderUseCase orderUseCase;

    @Mock
    private IUserExternalServicePort userExternalServicePort;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IJwtSecurityServicePort jwtSecurityServicePort;

    @Mock
    private IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort;

    @Mock
    private ISmsExternalService smsExternalService;

    @Test
    void placeOrder_WhenIsSuccessful() {
        String tokenEmail = "customer@email.com";
        Long customerId = 1L;

        Order oldOrder = Order.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.CANCELED)
                .chefId(null)
                .restaurantId(1L)
                .build();

        Order order = Order.builder()
                .id(null)
                .customerId(null)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        Order savedOrder = Order.builder()
                .id(1L)
                .customerId(customerId)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        List<Order> customerOrders = List.of(oldOrder);

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(customerId);
        when(orderPersistencePort.findAllByCustomerId(customerId))
                .thenReturn(customerOrders);
        when(orderPersistencePort.save(order))
                .thenReturn(savedOrder);

        Order result = orderUseCase.placeOrder(order);

        assertNotNull(result);
        assertEquals(savedOrder.getId(), result.getId());
        assertEquals(savedOrder.getCustomerId(), result.getCustomerId());
        assertEquals(savedOrder.getDate(), result.getDate());
        assertEquals(savedOrder.getStatus(), result.getStatus());
        assertEquals(savedOrder.getChefId(), result.getChefId());
        assertEquals(savedOrder.getRestaurantId(), result.getRestaurantId());
    }

    @Test
    void placeOrder_WhenUserHasOrderInPreparation_ShouldThrowCustomerHasActiveOrderException() {
        String tokenEmail = "customer@email.com";
        Long customerId = 1L;

        Order oldOrder = Order.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        Order order = Order.builder()
                .id(null)
                .customerId(null)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        List<Order> customerOrders = List.of(oldOrder);

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(customerId);
        when(orderPersistencePort.findAllByCustomerId(customerId))
                .thenReturn(customerOrders);

        CustomerHasActiveOrderException result = assertThrows(CustomerHasActiveOrderException.class, () -> orderUseCase.placeOrder(order));

        assertEquals(ExceptionConstants.CUSTOMER_HAS_ACTIVE_ORDER_EXCEPTION, result.getMessage());
    }

    @Test
    void placeOrder_WhenUserHasPendingOrder_ShouldThrowCustomerHasActiveOrderException() {
        String tokenEmail = "customer@email.com";
        Long customerId = 1L;

        Order oldOrder = Order.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        Order order = Order.builder()
                .id(null)
                .customerId(null)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        List<Order> customerOrders = List.of(oldOrder);

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(customerId);
        when(orderPersistencePort.findAllByCustomerId(customerId))
                .thenReturn(customerOrders);

        CustomerHasActiveOrderException result = assertThrows(CustomerHasActiveOrderException.class, () -> orderUseCase.placeOrder(order));

        assertEquals(ExceptionConstants.CUSTOMER_HAS_ACTIVE_ORDER_EXCEPTION, result.getMessage());
    }

    @Test
    void placeOrder_WhenUserHasOrderReady_ShouldThrowCustomerHasActiveOrderException() {
        String tokenEmail = "customer@email.com";
        Long customerId = 1L;

        Order oldOrder = Order.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.READY)
                .chefId(null)
                .restaurantId(1L)
                .build();

        Order order = Order.builder()
                .id(null)
                .customerId(null)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        List<Order> customerOrders = List.of(oldOrder);

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(customerId);
        when(orderPersistencePort.findAllByCustomerId(customerId))
                .thenReturn(customerOrders);

        CustomerHasActiveOrderException result = assertThrows(CustomerHasActiveOrderException.class, () -> orderUseCase.placeOrder(order));

        assertEquals(ExceptionConstants.CUSTOMER_HAS_ACTIVE_ORDER_EXCEPTION, result.getMessage());
    }

    @Test
    void getAllOrders_WhenIsSuccessful() {
        int page = 0;
        int pageSize = 10;
        OrderStatusEnum status = OrderStatusEnum.PENDING;
        String tokenEmail = "customer@email.com";
        Long employeeId = 1L;
        Long restaurantId = 1L;

        Restaurant restaurant = Restaurant.builder()
                .id(restaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(restaurant)
                .build();

        Order order = Order.builder()
                .id(1L)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(restaurantId)
                .build();

        List<Order> orders = List.of(order);

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);
        when(orderPersistencePort.findAll(page, pageSize, status, restaurantId))
                .thenReturn(orders);

        List<Order> result = orderUseCase.getAllOrders(page, pageSize, status);

        assertEquals(orders.size(), result.size());
        assertEquals(orders.get(0).getRestaurantId(), result.get(0).getRestaurantId());
        assertEquals(orders.get(0).getCustomerId(), result.get(0).getCustomerId());
        assertEquals(orders.get(0).getStatus(), result.get(0).getStatus());
    }

    @Test
    void assignOrder_WhenIsSuccessful() {
        Long orderId = 1L;
        String tokenEmail = "test@email.com";
        Long employeeId = 1L;
        Long orderRestaurantId = 1L;
        Long employeeRestaurantId = 1L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(orderRestaurantId)
                .build();

        Order updatedOrder = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(employeeId)
                .restaurantId(orderRestaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(Restaurant.builder().id(employeeRestaurantId).build())
                .build();

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(orderPersistencePort.findById(orderId))
                .thenReturn(order);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);
        when(orderPersistencePort.save(order))
                .thenReturn(updatedOrder);

        Order result = orderUseCase.assignOrder(orderId);

        assertNotNull(result);
        assertEquals(updatedOrder.getRestaurantId(), result.getRestaurantId());
        assertEquals(updatedOrder.getCustomerId(), result.getCustomerId());
        assertEquals(updatedOrder.getStatus(), result.getStatus());
        assertEquals(updatedOrder.getStatus(), result.getStatus());
        assertEquals(updatedOrder.getChefId(), result.getChefId());
        assertEquals(updatedOrder.getRestaurantId(), result.getRestaurantId());
    }

    @Test
    void assignOrder_WhenThrowOrderNotFromEmployeeRestaurantException() {
        Long orderId = 1L;
        String tokenEmail = "test@email.com";
        Long employeeId = 1L;
        Long orderRestaurantId = 1L;
        Long employeeRestaurantId = 2L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(orderRestaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(Restaurant.builder().id(employeeRestaurantId).build())
                .build();

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(orderPersistencePort.findById(orderId))
                .thenReturn(order);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);

        OrderNotFromEmployeeRestaurantException result = assertThrows(OrderNotFromEmployeeRestaurantException.class, () ->
                orderUseCase.assignOrder(orderId));

        assertNotNull(result);
        assertEquals(ExceptionConstants.ORDER_NOT_FROM_EMPLOYEE_RESTAURANT_EXCEPTION, result.getMessage());
    }

    @Test
    void assignOrder_WhenThrowInvalidOrderStatusException() {
        Long orderId = 1L;
        String tokenEmail = "test@email.com";
        Long employeeId = 1L;
        Long orderRestaurantId = 1L;
        Long employeeRestaurantId = 1L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(null)
                .restaurantId(orderRestaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(Restaurant.builder().id(employeeRestaurantId).build())
                .build();

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(orderPersistencePort.findById(orderId))
                .thenReturn(order);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);

        InvalidOrderStatusException result = assertThrows(InvalidOrderStatusException.class, () ->
                orderUseCase.assignOrder(orderId));

        assertNotNull(result);
        assertEquals(ExceptionConstants.INVALID_ORDER_STATUS_EXCEPTION, result.getMessage());
    }

    @Test
    void markOrderReady_WhenIsSuccessful() {
        String customerCellPhoneNumber = "3221234322";
        String tokenEmail = "test@email.com";
        Long orderId = 1L;
        Long employeeId = 1L;
        Long orderRestaurantId = 1L;
        Long employeeRestaurantId = 1L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(employeeId)
                .restaurantId(orderRestaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(Restaurant.builder().id(employeeRestaurantId).build())
                .build();

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(orderPersistencePort.findById(orderId))
                .thenReturn(order);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);
        when(userExternalServicePort.getCellPhoneNumberById(order.getCustomerId()))
                .thenReturn(customerCellPhoneNumber);
        when(smsExternalService.notifyOrderReady(eq(customerCellPhoneNumber), anyInt()))
                .thenReturn(true);

        int result = orderUseCase.markOrderReady(orderId);

        assertTrue(result > 99999);
        assertTrue(result < 1000000);
    }

    @Test
    void markOrderReady_WhenThrowOrderNotFromEmployeeRestaurantException() {
        String tokenEmail = "test@email.com";
        Long orderId = 1L;
        Long employeeId = 1L;
        Long orderRestaurantId = 1L;
        Long employeeRestaurantId = 2L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(employeeId)
                .restaurantId(orderRestaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(Restaurant.builder().id(employeeRestaurantId).build())
                .build();

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(orderPersistencePort.findById(orderId))
                .thenReturn(order);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);

        OrderNotFromEmployeeRestaurantException result = assertThrows(OrderNotFromEmployeeRestaurantException.class, () ->
                orderUseCase.markOrderReady(orderId));

        assertEquals(ExceptionConstants.ORDER_NOT_FROM_EMPLOYEE_RESTAURANT_EXCEPTION, result.getMessage());
    }

    @Test
    void markOrderReady_WhenThrowOrderNotAssignedToEmployeeException() {
        String tokenEmail = "test@email.com";
        Long orderId = 1L;
        Long employeeId = 1L;
        Long orderRestaurantId = 1L;
        Long employeeRestaurantId = 1L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(2L)
                .restaurantId(orderRestaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(Restaurant.builder().id(employeeRestaurantId).build())
                .build();

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(orderPersistencePort.findById(orderId))
                .thenReturn(order);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);

        OrderNotAssignedToEmployeeException result = assertThrows(OrderNotAssignedToEmployeeException.class, () ->
                orderUseCase.markOrderReady(orderId));

        assertEquals(ExceptionConstants.ORDER_NOT_ASSIGNED_TO_EMPLOYEE_EXCEPTION, result.getMessage());
    }

    @Test
    void markOrderReady_WhenThrowInvalidOrderStatusException() {
        String tokenEmail = "test@email.com";
        Long orderId = 1L;
        Long employeeId = 1L;
        Long orderRestaurantId = 1L;
        Long employeeRestaurantId = 1L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(employeeId)
                .restaurantId(orderRestaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(Restaurant.builder().id(employeeRestaurantId).build())
                .build();

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(orderPersistencePort.findById(orderId))
                .thenReturn(order);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);

        InvalidOrderStatusException result = assertThrows(InvalidOrderStatusException.class, () ->
                orderUseCase.markOrderReady(orderId));

        assertEquals(ExceptionConstants.INVALID_ORDER_STATUS_EXCEPTION, result.getMessage());
    }

    @Test
    void markOrderReady_WhenThrowNotificationFailedException() {
        String customerCellPhoneNumber = "3221234322";
        String tokenEmail = "test@email.com";
        Long orderId = 1L;
        Long employeeId = 1L;
        Long orderRestaurantId = 1L;
        Long employeeRestaurantId = 1L;

        Order order = Order.builder()
                .id(orderId)
                .customerId(2L)
                .date(LocalDate.of(2024, 5, 17))
                .status(OrderStatusEnum.PREPARING)
                .chefId(employeeId)
                .restaurantId(orderRestaurantId)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(Restaurant.builder().id(employeeRestaurantId).build())
                .build();

        when(jwtSecurityServicePort.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.getUserIdByEmail(tokenEmail))
                .thenReturn(employeeId);
        when(orderPersistencePort.findById(orderId))
                .thenReturn(order);
        when(employeeAssignmentPersistencePort.findByEmployeeId(employeeId))
                .thenReturn(employeeAssignment);
        when(userExternalServicePort.getCellPhoneNumberById(order.getCustomerId()))
                .thenReturn(customerCellPhoneNumber);
        when(smsExternalService.notifyOrderReady(eq(customerCellPhoneNumber), anyInt()))
                .thenReturn(false);

        NotificationFailedException result = assertThrows(NotificationFailedException.class, () ->
                orderUseCase.markOrderReady(orderId));

        assertEquals(ExceptionConstants.NOTIFICATION_FAILED_EXCEPTION, result.getMessage());
    }
}