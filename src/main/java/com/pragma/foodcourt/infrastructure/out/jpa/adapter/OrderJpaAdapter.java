package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.domain.spi.IOrderPersistencePort;
import com.pragma.foodcourt.infrastructure.exception.OrderNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.OrderRepository;
import com.pragma.foodcourt.infrastructure.out.jpa.specification.IOrderSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final OrderRepository orderRepository;
    private final IOrderSpecification orderSpecification;
    private final ModelMapper modelMapper;

    @Override
    public Order save(Order order) {
        OrderEntity mappedOrderEntity = modelMapper.map(order, OrderEntity.class);
        OrderEntity savedOrderEntity = orderRepository.save(mappedOrderEntity);

        return modelMapper.map(savedOrderEntity, Order.class);
    }

    @Override
    public List<Order> findAllByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId).stream()
                .map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .toList();
    }

    @Override
    public List<Order> findAll(int page, int pageSize, OrderStatusEnum status, Long restaurantId) {
        Specification<OrderEntity> specification = Specification.where(orderSpecification.hasStatus(status))
                .and(orderSpecification.hasRestaurantId(restaurantId));

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<OrderEntity> orderEntities = orderRepository.findAll(specification, pageable);

        return orderEntities
                .map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .toList();
    }

    @Override
    public Order findById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(ExceptionConstants.ORDER_NOT_FOUND));
        return modelMapper.map(orderEntity, Order.class);
    }

    @Override
    public List<Order> findAllByRestaurantId(Long restaurantId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByRestaurantId(restaurantId);
        return orderEntities.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .toList();
    }
}
