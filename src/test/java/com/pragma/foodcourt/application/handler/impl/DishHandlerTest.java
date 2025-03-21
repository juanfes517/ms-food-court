package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.CreateDishRequestDto;
import com.pragma.foodcourt.application.dto.request.UpdateDishRequestDto;
import com.pragma.foodcourt.application.dto.response.CategoryResponseDto;
import com.pragma.foodcourt.application.dto.response.DishResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantBasicInfoResponseDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    @Test
    void updateDish_WhenPriceIsNotNull_AndDescriptionIsNull() {
        UpdateDishRequestDto updateDishRequestDto = UpdateDishRequestDto.builder()
                .dishId(1L)
                .price(10)
                .description(null)
                .build();

        Dish updatedDish = Dish.builder()
                .id(1L)
                .name("dish name")
                .price(updateDishRequestDto.getPrice())
                .description("dish description")
                .imageUrl("dish image url")
                .category(new Category())
                .restaurant(new Restaurant())
                .active(true)
                .build();

        DishResponseDto dishResponseDto = DishResponseDto.builder()
                .id(1L)
                .name("dish name")
                .price(updateDishRequestDto.getPrice())
                .description("dish description")
                .imageUrl("dish image url")
                .category(new CategoryResponseDto())
                .restaurant(new RestaurantResponseDto())
                .active(true)
                .build();

        when(dishServicePort.updateDish(updateDishRequestDto.getDishId(), updateDishRequestDto.getPrice(), updateDishRequestDto.getDescription()))
                .thenReturn(updatedDish);
        when(modelMapper.map(updatedDish, DishResponseDto.class))
                .thenReturn(dishResponseDto);

        DishResponseDto result = dishHandler.updateDish(updateDishRequestDto);

        assertNotNull(result);
        assertEquals(updateDishRequestDto.getPrice(), result.getPrice());
        assertEquals("dish description", result.getDescription());
    }

    @Test
    void updateDish_WhenPriceIsNull_AndDescriptionIsNotNull() {
        UpdateDishRequestDto updateDishRequestDto = UpdateDishRequestDto.builder()
                .dishId(1L)
                .price(null)
                .description("New description")
                .build();

        Dish updatedDish = Dish.builder()
                .id(1L)
                .name("dish name")
                .price(10)
                .description(updateDishRequestDto.getDescription())
                .imageUrl("dish image url")
                .category(new Category())
                .restaurant(new Restaurant())
                .active(true)
                .build();

        DishResponseDto dishResponseDto = DishResponseDto.builder()
                .id(1L)
                .name("dish name")
                .price(10)
                .description(updateDishRequestDto.getDescription())
                .imageUrl("dish image url")
                .category(new CategoryResponseDto())
                .restaurant(new RestaurantResponseDto())
                .active(true)
                .build();

        when(dishServicePort.updateDish(updateDishRequestDto.getDishId(), updateDishRequestDto.getPrice(), updateDishRequestDto.getDescription()))
                .thenReturn(updatedDish);
        when(modelMapper.map(updatedDish, DishResponseDto.class))
                .thenReturn(dishResponseDto);

        DishResponseDto result = dishHandler.updateDish(updateDishRequestDto);

        assertNotNull(result);
        assertEquals(10, result.getPrice());
        assertEquals(updateDishRequestDto.getDescription(), result.getDescription());
    }

    @Test
    void updateDish_WhenPriceIsNotNull_AndDescriptionIsNotNull() {
        UpdateDishRequestDto updateDishRequestDto = UpdateDishRequestDto.builder()
                .dishId(1L)
                .price(20)
                .description("New description")
                .build();

        Dish updatedDish = Dish.builder()
                .id(1L)
                .name("dish name")
                .price(updateDishRequestDto.getPrice())
                .description(updateDishRequestDto.getDescription())
                .imageUrl("dish image url")
                .category(new Category())
                .restaurant(new Restaurant())
                .active(true)
                .build();

        DishResponseDto dishResponseDto = DishResponseDto.builder()
                .id(1L)
                .name("dish name")
                .price(updateDishRequestDto.getPrice())
                .description(updateDishRequestDto.getDescription())
                .imageUrl("dish image url")
                .category(new CategoryResponseDto())
                .restaurant(new RestaurantResponseDto())
                .active(true)
                .build();

        when(dishServicePort.updateDish(updateDishRequestDto.getDishId(), updateDishRequestDto.getPrice(), updateDishRequestDto.getDescription()))
                .thenReturn(updatedDish);
        when(modelMapper.map(updatedDish, DishResponseDto.class))
                .thenReturn(dishResponseDto);

        DishResponseDto result = dishHandler.updateDish(updateDishRequestDto);

        assertNotNull(result);
        assertEquals(updateDishRequestDto.getPrice(), result.getPrice());
        assertEquals(updateDishRequestDto.getDescription(), result.getDescription());
    }

    @Test
    void updateDishStatus_WhenIsSuccessful() {
        Long dishId = 1L;
        boolean status = true;

        Dish savedDish = Dish.builder()
                .id(dishId)
                .name("Dish name")
                .category(new Category())
                .description("Dish description")
                .price(5)
                .restaurant(new Restaurant())
                .imageUrl("Image Url")
                .active(status)
                .build();

        DishResponseDto mappedDish = DishResponseDto.builder()
                .id(dishId)
                .name("Dish name")
                .category(new CategoryResponseDto())
                .description("Dish description")
                .price(5)
                .restaurant(new RestaurantResponseDto())
                .imageUrl("Image Url")
                .active(status)
                .build();

        when(dishServicePort.updateDishStatus(dishId, status))
                .thenReturn(savedDish);
        when(modelMapper.map(savedDish, DishResponseDto.class))
                .thenReturn(mappedDish);

        DishResponseDto result = dishHandler.updateDishStatus(dishId, status);

        assertNotNull(result);
        assertEquals(mappedDish.getId(), result.getId());
        assertEquals(mappedDish.getName(), result.getName());
        assertEquals(mappedDish.getDescription(), result.getDescription());
        assertEquals(mappedDish.getPrice(), result.getPrice());
        assertEquals(mappedDish.getImageUrl(), result.getImageUrl());
        assertEquals(mappedDish.isActive(), result.isActive());
    }

    @Test
    void findAllDishes_whenIsSuccessful() {
        String categoryName = "Category";
        Long restaurantId = 1L;

        Dish dish = Dish.builder()
                .id(1L)
                .name("Dish name")
                .category(new Category(1L, categoryName, null))
                .description("Dish description")
                .price(5)
                .restaurant(Restaurant.builder().id(restaurantId).build())
                .imageUrl("Image Url")
                .active(true)
                .build();

        DishResponseDto dishResponseDto = DishResponseDto.builder()
                .id(1L)
                .name("Dish name")
                .category(new CategoryResponseDto(1L, categoryName, null))
                .description("Dish description")
                .price(5)
                .restaurant(RestaurantResponseDto.builder().id(restaurantId).build())
                .imageUrl("Image Url")
                .active(true)
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        List<Dish> dishes = List.of(dish);
        Page<Dish> dishPage = new PageImpl<>(dishes, pageable, dishes.size());

        when(dishServicePort.findAllDishes(pageable, categoryName, restaurantId))
                .thenReturn(dishPage);
        when(modelMapper.map(dish, DishResponseDto.class))
                .thenReturn(dishResponseDto);

        Page<DishResponseDto> result = dishHandler.findAllDishes(pageable, categoryName, restaurantId);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(dishPage.getContent().get(0).getName(), result.getContent().get(0).getName());
        assertEquals(dishPage.getContent().get(0).getCategory().getName(), result.getContent().get(0).getCategory().getName());
        assertEquals(dishPage.getContent().get(0).getRestaurant().getId(), result.getContent().get(0).getRestaurant().getId());
    }
}