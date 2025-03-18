package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.domain.spi.ICategoryPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CategoryUseCaseTest {

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Test
    void findByName_WhenIsSuccessful() {
        String categoryName = "category";

        Category category = Category.builder()
                .id(1L)
                .name("category")
                .description("category description")
                .build();

        when(categoryPersistencePort.findByName(categoryName))
                .thenReturn(category);

        Category result = categoryPersistencePort.findByName(categoryName);

        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
        assertEquals(category.getName(), result.getName());
        assertEquals(category.getDescription(), result.getDescription());
    }
}