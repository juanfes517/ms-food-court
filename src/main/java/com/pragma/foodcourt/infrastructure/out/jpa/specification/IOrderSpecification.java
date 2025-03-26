package com.pragma.foodcourt.infrastructure.out.jpa.specification;

import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

public interface IOrderSpecification {

    Specification<OrderEntity> hasStatus(OrderStatusEnum status);

    Specification<OrderEntity> hasRestaurantId(Long restaurantId);
}
