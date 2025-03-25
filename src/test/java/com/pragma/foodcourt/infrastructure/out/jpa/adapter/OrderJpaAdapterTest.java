package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderJpaAdapterTest {

    @InjectMocks
    private OrderJpaAdapter orderJpaAdapter;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void save_WhenIsSuccessful() {
        Order order = Order.builder()
                .id(null)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        OrderEntity mappedOrderEntity = OrderEntity.builder()
                .id(null)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        OrderEntity savedOrderEntity = OrderEntity.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        Order savedOrder = Order.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        when(modelMapper.map(order, OrderEntity.class))
                .thenReturn(mappedOrderEntity);
        when(orderRepository.save(mappedOrderEntity))
                .thenReturn(savedOrderEntity);
        when(modelMapper.map(savedOrderEntity, Order.class))
                .thenReturn(savedOrder);

        Order result = orderJpaAdapter.save(order);

        assertNotNull(result);
        assertEquals(savedOrder.getId(), result.getId());
        assertEquals(savedOrder.getCustomerId(), result.getCustomerId());
        assertEquals(savedOrder.getDate(), result.getDate());
        assertEquals(savedOrder.getStatus(), result.getStatus());
        assertEquals(savedOrder.getChefId(), result.getChefId());
        assertEquals(savedOrder.getRestaurantId(), result.getRestaurantId());
    }

    @Test
    void findAllByCustomerId_WhenIsSuccessful() {
        Long customerId = 1L;

        OrderEntity orderEntity = OrderEntity.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        Order order = Order.builder()
                .id(1L)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        List<OrderEntity> orderEntities = List.of(orderEntity);
        List<Order> orders = List.of(order);

        when(orderRepository.findAllByCustomerId(customerId))
                .thenReturn(orderEntities);
        when(modelMapper.map(orderEntity, Order.class))
                .thenReturn(order);

        List<Order> result = orderJpaAdapter.findAllByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(orders.size(), result.size());
        assertEquals(orders.get(0), result.get(0));
    }
}