package com.pragma.foodcourt.infrastructure.out.jpa.specification;

import com.pragma.foodcourt.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.jpa.domain.Specification;

public interface IDishSpecification {

    Specification<DishEntity> hasRestaurant(Long restaurantId);

    Specification<DishEntity> hasCategory(String categoryName);
}
