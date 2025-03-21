package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishServicePort {

    Dish saveDish(Dish dish);

    Dish updateDish(Long dishId, Integer price, String description);

    Dish updateDishStatus(Long dishId, boolean status);

    Page<Dish> findAllDishes(Pageable pageable, String categoryName, Long restaurantId);
}
