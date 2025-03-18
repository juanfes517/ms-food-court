package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.Category;

public interface ICategoryPersistencePort {

    Category findByName(String name);
}
