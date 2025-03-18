package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.infrastructure.exception.CategoryNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void findByName_WhenIsSuccessful() {
        String name = "category name";

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name(name)
                .description("category description")
                .build();

        Category category = Category.builder()
                .id(1L)
                .name(name)
                .description("category description")
                .build();

        when(categoryRepository.findByName(name))
                .thenReturn(Optional.of(categoryEntity));
        when(modelMapper.map(categoryEntity, Category.class))
                .thenReturn(category);

        Category result = categoryJpaAdapter.findByName(name);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
        assertEquals(category.getDescription(), result.getDescription());
    }

    @Test
    void findByName_ShouldThrowCategoryNotFoundException() {
        String name = "category name";

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> categoryJpaAdapter.findByName(name));

        assertEquals(ExceptionConstants.CATEGORY_NOT_FOUND, exception.getMessage());

    }
}