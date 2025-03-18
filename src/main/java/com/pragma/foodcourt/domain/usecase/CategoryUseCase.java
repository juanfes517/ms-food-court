package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.ICategoryServicePort;
import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    @Override
    public Category findByName(String name) {
        return categoryPersistencePort.findByName(name);
    }
}
