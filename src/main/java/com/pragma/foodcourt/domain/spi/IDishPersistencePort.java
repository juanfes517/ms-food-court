package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.Dish;

public interface IDishPersistencePort {

    Dish save(Dish dish);
}
