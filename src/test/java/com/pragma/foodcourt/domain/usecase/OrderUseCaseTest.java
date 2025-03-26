package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.exception.CustomerHasActiveOrderException;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.domain.spi.IJwtSecurityServicePort;
import com.pragma.foodcourt.domain.spi.IOrderPersistencePort;
import com.pragma.foodcourt.domain.spi.IUserExternalServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
}