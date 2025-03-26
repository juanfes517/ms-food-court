package com.pragma.foodcourt.infrastructure.out.jpa.specification.impl;

import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.specification.IOrderSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class OrderSpecification implements IOrderSpecification {

    @Override
    public Specification<OrderEntity> hasStatus(OrderStatusEnum status) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status));
    }

    @Override
    public Specification<OrderEntity> hasRestaurantId(Long restaurantId) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("restaurant"), restaurantId));
    }
}
