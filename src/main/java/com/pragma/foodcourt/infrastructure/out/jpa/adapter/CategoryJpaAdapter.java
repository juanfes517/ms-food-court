package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.domain.spi.ICategoryPersistencePort;
import com.pragma.foodcourt.infrastructure.exception.CategoryNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Category findByName(String name) {

        CategoryEntity categoryEntity = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(ExceptionConstants.CATEGORY_NOT_FOUND));

        return modelMapper.map(categoryEntity, Category.class);
    }
}
