package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.spi.IOrderPersistencePort;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {

    private final OrderRepository orderRepository;
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
                .collect(Collectors.toList());
    }
}
