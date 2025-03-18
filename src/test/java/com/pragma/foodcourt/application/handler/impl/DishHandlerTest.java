package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.CreateDishRequestDto;
import com.pragma.foodcourt.application.dto.response.CategoryResponseDto;
import com.pragma.foodcourt.application.dto.response.DishResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantResponseDto;
import com.pragma.foodcourt.domain.api.ICategoryServicePort;
import com.pragma.foodcourt.domain.api.IDishServicePort;
import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishHandlerTest {

    @InjectMocks
    private DishHandler dishHandler;

    @Mock
    private IDishServicePort dishServicePort;

    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private IRestaurantServicePort restaurantServicePort;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveDish_whenIsSuccessful() {
        CreateDishRequestDto createDishRequestDto = CreateDishRequestDto.builder()
                .name("dish name")
                .price(10)
                .description("dish description")
                .imageUrl("dish image url")
                .categoryName("category name")
                .restaurantId(1L)
                .build();

        Category category = Category.builder()
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

        CategoryResponseDto categoryResponseDto = CategoryResponseDto.builder()
                .id(1L)
                .name("category name")
                .description("category description")
                .build();

        RestaurantResponseDto restaurantResponseDto = RestaurantResponseDto.builder()
                .id(1L)
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("1234565")
                .ownerId(2L)
                .build();

        Dish dishSaved = Dish.builder()
                .id(1L)
                .name(createDishRequestDto.getName())
                .price(createDishRequestDto.getPrice())
                .description(createDishRequestDto.getDescription())
                .imageUrl(createDishRequestDto.getImageUrl())
                .category(category)
                .restaurant(restaurant)
                .active(true)
                .build();

        DishResponseDto mappedDish = DishResponseDto.builder()
                .id(1L)
                .name(createDishRequestDto.getName())
                .price(createDishRequestDto.getPrice())
                .description(createDishRequestDto.getDescription())
                .imageUrl(createDishRequestDto.getImageUrl())
                .category(categoryResponseDto)
                .restaurant(restaurantResponseDto)
                .active(true)
                .build();

        when(categoryServicePort.findByName(createDishRequestDto.getCategoryName()))
                .thenReturn(category);
        when(restaurantServicePort.findRestaurantById(createDishRequestDto.getRestaurantId()))
                .thenReturn(restaurant);
        when(dishServicePort.saveDish(any(Dish.class)))
                .thenReturn(dishSaved);
        when(modelMapper.map(dishSaved, DishResponseDto.class))
                .thenReturn(mappedDish);

        DishResponseDto result = dishHandler.saveDish(createDishRequestDto);

        assertNotNull(result);
        assertEquals(mappedDish.getId(), result.getId());
        assertEquals(mappedDish.getName(), result.getName());
        assertEquals(mappedDish.getPrice(), result.getPrice());
        assertEquals(mappedDish.getDescription(), result.getDescription());
        assertEquals(mappedDish.getImageUrl(), result.getImageUrl());
        assertEquals(mappedDish.getCategory(), result.getCategory());
        assertEquals(mappedDish.getRestaurant(), result.getRestaurant());
        assertEquals(mappedDish.isActive(), result.isActive());

    }
}