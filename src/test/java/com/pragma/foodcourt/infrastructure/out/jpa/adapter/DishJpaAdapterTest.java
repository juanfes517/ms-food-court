package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.infrastructure.exception.DishNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.DishRepository;
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
class DishJpaAdapterTest {

    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void save_WhenIsSuccessful() {
        Category category = Category.builder()
                .id(1L)
                .name("category name")
                .description("category description")
                .build();

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(1L)
                .name("category name")
                .description("category description")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("1234565")
                .ownerId(2L)
                .build();

        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("1234565")
                .ownerId(2L)
                .build();

        Dish dish = Dish.builder()
                .name("dish name")
                .category(category)
                .description("dish description")
                .price(5)
                .restaurant(restaurant)
                .imageUrl("image URL")
                .active(true)
                .build();

        DishEntity mappedDishEntity = DishEntity.builder()
                .name("dish name")
                .category(categoryEntity)
                .description("dish description")
                .price(5)
                .restaurant(restaurantEntity)
                .imageUrl("image URL")
                .active(true)
                .build();

        DishEntity savedDishEntity = DishEntity.builder()
                .id(1L)
                .name("dish name")
                .category(categoryEntity)
                .description("dish description")
                .price(5)
                .restaurant(restaurantEntity)
                .imageUrl("image URL")
                .active(true)
                .build();

        Dish mappedDish = Dish.builder()
                .id(1L)
                .name("dish name")
                .category(category)
                .description("dish description")
                .price(5)
                .restaurant(restaurant)
                .imageUrl("image URL")
                .active(true)
                .build();

        when(modelMapper.map(dish, DishEntity.class))
                .thenReturn(mappedDishEntity);
        when(dishRepository.save(mappedDishEntity))
                .thenReturn(savedDishEntity);
        when(modelMapper.map(savedDishEntity, Dish.class))
                .thenReturn(mappedDish);

        Dish result = dishJpaAdapter.save(dish);

        assertNotNull(result);
        assertEquals(mappedDish.getId(), result.getId());
        assertEquals(mappedDish.getName(), result.getName());
        assertEquals(mappedDish.getDescription(), result.getDescription());
        assertEquals(mappedDish.getPrice(), result.getPrice());
        assertEquals(mappedDish.getImageUrl(), result.getImageUrl());
        assertEquals(mappedDish.isActive(), result.isActive());
        assertEquals(mappedDish.getCategory(), result.getCategory());
        assertEquals(mappedDish.getRestaurant(), result.getRestaurant());
    }

    @Test
    void findById_WhenIsSuccessful() {
        Long id = 1L;

        DishEntity dishEntity = DishEntity.builder()
                .id(id)
                .name("dish name")
                .category(new CategoryEntity())
                .description("dish description")
                .price(5)
                .restaurant(new RestaurantEntity())
                .imageUrl("image URL")
                .active(true)
                .build();

        Dish dish = Dish.builder()
                .id(id)
                .name("dish name")
                .category(new Category())
                .description("dish description")
                .price(5)
                .restaurant(new Restaurant())
                .imageUrl("image URL")
                .active(true)
                .build();

        when(dishRepository.findById(id))
                .thenReturn(Optional.of(dishEntity));
        when(modelMapper.map(dishEntity, Dish.class))
                .thenReturn(dish);

        Dish result = dishJpaAdapter.findById(id);

        assertNotNull(result);
        assertEquals(dishEntity.getId(), result.getId());
        assertEquals(dishEntity.getName(), result.getName());
        assertEquals(dishEntity.getDescription(), result.getDescription());
        assertEquals(dishEntity.getPrice(), result.getPrice());
        assertEquals(dishEntity.getImageUrl(), result.getImageUrl());
        assertEquals(dishEntity.isActive(), result.isActive());
    }

    @Test
    void findById_ThrowDishNotFoundException() {
        Long id = 1L;

        DishNotFoundException result = assertThrows(DishNotFoundException.class, () -> dishJpaAdapter.findById(id));

        assertEquals(ExceptionConstants.DISH_NOT_FOUND, result.getMessage());
    }
}