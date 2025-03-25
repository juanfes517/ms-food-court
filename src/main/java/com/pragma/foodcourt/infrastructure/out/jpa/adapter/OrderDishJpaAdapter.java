package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.OrderDish;
import com.pragma.foodcourt.domain.spi.IOrderDishPersistencePort;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.OrderDishRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDishJpaAdapter implements IOrderDishPersistencePort {

    private final OrderDishRepository orderDishRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDish save(OrderDish orderDish) {
        OrderDishEntity mappedOrderDishEntity = modelMapper.map(orderDish, OrderDishEntity.class);
        OrderDishEntity savedOrderDishEntity = orderDishRepository.save(mappedOrderDishEntity);

        return modelMapper.map(savedOrderDishEntity, OrderDish.class);
    }
}
