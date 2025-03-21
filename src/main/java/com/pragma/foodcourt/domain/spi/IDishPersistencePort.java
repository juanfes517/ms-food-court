package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishPersistencePort {

    Dish save(Dish dish);

    Dish findById(Long id);

    Page<Dish> findAll(Pageable pageable, String categoryName, Long restaurantId);
}
