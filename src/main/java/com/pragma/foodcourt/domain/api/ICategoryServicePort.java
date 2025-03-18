package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.Category;

public interface ICategoryServicePort {

    Category findByName(String name);
}
