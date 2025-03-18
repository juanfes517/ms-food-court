package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.Dish;

public interface IDishServicePort {

    Dish saveDish(Dish dish);

    Dish updateDish(Long dishId, Integer price, String description);
}
