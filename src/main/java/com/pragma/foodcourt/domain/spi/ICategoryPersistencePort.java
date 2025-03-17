package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.domain.model.Dish;

public interface ICategoryPersistencePort {

    Category findByName(String name);

    Category save(Category category);
}
