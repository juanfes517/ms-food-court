package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.infrastructure.exception.OrderNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.OrderRepository;
import com.pragma.foodcourt.infrastructure.out.jpa.specification.IOrderSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderJpaAdapterTest {

    @InjectMocks
    private OrderJpaAdapter orderJpaAdapter;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private IOrderSpecification orderSpecification;

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

    @Test
    void findAll_WhenIsSuccessful() {
        int page = 0;
        int pageSize = 10;
        OrderStatusEnum status = OrderStatusEnum.PENDING;
        Long restaurantId = 1L;

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

        Specification<OrderEntity> specification = Specification.where(null);
        Pageable pageable = PageRequest.of(page, pageSize);

        List<OrderEntity > orderEntities = List.of(orderEntity);
        Page<OrderEntity> orderEntityPage = new PageImpl<>(orderEntities, pageable, orderEntities.size());

        List<Order> orders = List.of(order);

        when(orderSpecification.hasStatus(status))
                .thenReturn(specification);
        when(orderSpecification.hasRestaurantId(restaurantId))
                .thenReturn(specification);
        when(orderRepository.findAll(any(Specification.class), eq(pageable)))
                .thenReturn(orderEntityPage);
        when(modelMapper.map(orderEntity, Order.class))
                .thenReturn(order);

        List<Order> result = orderJpaAdapter.findAll(page, pageSize, status, restaurantId);

        assertNotNull(result);
        assertEquals(orderEntities.size(), result.size());
        assertEquals(orders.get(0), result.get(0));
    }

    @Test
    void findById_WhenIsSuccessful() {
        Long orderId = 1L;

        OrderEntity orderEntity = OrderEntity.builder()
                .id(orderId)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        Order order = Order.builder()
                .id(orderId)
                .customerId(1L)
                .date(LocalDate.of(2000, 5, 17))
                .status(OrderStatusEnum.PENDING)
                .chefId(null)
                .restaurantId(1L)
                .build();

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(orderEntity));
        when(modelMapper.map(orderEntity, Order.class))
                .thenReturn(order);

        Order result = orderJpaAdapter.findById(orderId);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getCustomerId(), result.getCustomerId());
        assertEquals(order.getDate(), result.getDate());
        assertEquals(order.getStatus(), result.getStatus());
        assertEquals(order.getChefId(), result.getChefId());
        assertEquals(order.getRestaurantId(), result.getRestaurantId());
    }

    @Test
    void findById_WhenThrowOrderNotFoundException() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.empty());

        OrderNotFoundException result = assertThrows(OrderNotFoundException.class, () -> orderJpaAdapter.findById(orderId));

        assertNotNull(result);
        assertEquals(ExceptionConstants.ORDER_NOT_FOUND, result.getMessage());
    }
}