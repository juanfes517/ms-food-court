package com.pragma.foodcourt.infrastructure.out.jpa.specification.impl;

import com.pragma.foodcourt.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.specification.IDishSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class DishSpecification implements IDishSpecification {

    @Override
    public Specification<DishEntity> hasRestaurant(Long restaurantId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("restaurant").get("id"), restaurantId);
    }

    @Override
    public Specification<DishEntity> hasCategory(String categoryName) {
        return (root, query, criteriaBuilder) ->
                categoryName == null || categoryName.isEmpty() ? null : criteriaBuilder.equal(root.get("category").get("name"), categoryName);
    }
}
