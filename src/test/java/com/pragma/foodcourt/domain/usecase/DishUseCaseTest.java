package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.exception.InvalidPriceException;
import com.pragma.foodcourt.domain.exception.InvalidRestaurantOwnerException;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.IDishPersistencePort;
import com.pragma.foodcourt.domain.spi.IJwtSecurityServicePort;
import com.pragma.foodcourt.domain.spi.IUserExternalServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @InjectMocks
    private DishUseCase dishUseCase;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private IJwtSecurityServicePort jwtServiceUtils;

    @Mock
    private IUserExternalServicePort userExternalServicePort;

    @Test
    void saveDish_WhenItIsSaved() {
        Category category = Category.builder()
                .id(1L)
                .name("Category name")
                .description("Category description")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        Dish dish = Dish.builder()
                .name("Dish name")
                .category(category)
                .description("Dish description")
                .price(2)
                .restaurant(restaurant)
                .imageUrl("Image Url")
                .active(true)
                .build();

        Dish dishSaved = Dish.builder()
                .id(1L)
                .name("Dish name")
                .category(category)
                .description("Dish description")
                .price(2)
                .restaurant(restaurant)
                .imageUrl("Image Url")
                .active(true)
                .build();

        String tokenEmail = "test@mail.com";
        Long restaurantOwnerId = dish.getRestaurant().getOwnerId();

        when(jwtServiceUtils.extractSubjectFromToken())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.userHasEmail(restaurantOwnerId, tokenEmail))
                .thenReturn(true);
        when(dishPersistencePort.save(dish))
                .thenReturn(dishSaved);

        Dish result = dishUseCase.saveDish(dish);

        assertNotNull(result);
        assertEquals(dishSaved.getId(), result.getId());
        assertEquals(dishSaved.getName(), result.getName());
        assertEquals(dishSaved.getDescription(), result.getDescription());
        assertEquals(dishSaved.getPrice(), result.getPrice());
        assertEquals(dishSaved.getImageUrl(), result.getImageUrl());
        assertEquals(dishSaved.isActive(), result.isActive());
    }

    @Test
    void saveDish_WhenThrowInvalidRestaurantOwnerException() {
        Category category = Category.builder()
                .id(1L)
                .name("Category name")
                .description("Category description")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        Dish dish = Dish.builder()
                .name("Dish name")
                .category(category)
                .description("Dish description")
                .price(2)
                .restaurant(restaurant)
                .imageUrl("Image Url")
                .active(true)
                .build();

        String tokenEmail = "test@mail.com";
        Long restaurantOwnerId = dish.getRestaurant().getOwnerId();

        when(jwtServiceUtils.extractSubjectFromToken())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.userHasEmail(restaurantOwnerId, tokenEmail))
                .thenReturn(false);

        InvalidRestaurantOwnerException result = assertThrows(InvalidRestaurantOwnerException.class, () -> dishUseCase.saveDish(dish));

        assertEquals(ExceptionConstants.INVALID_RESTAURANT_OWNER_MESSAGE, result.getMessage());
    }

    @Test
    void saveDish_WhenThrowInvalidPriceException() {
        Category category = Category.builder()
                .id(1L)
                .name("Category name")
                .description("Category description")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        Dish dish = Dish.builder()
                .name("Dish name")
                .category(category)
                .description("Dish description")
                .price(-2)
                .restaurant(restaurant)
                .imageUrl("Image Url")
                .active(true)
                .build();

        String tokenEmail = "test@mail.com";
        Long restaurantOwnerId = dish.getRestaurant().getOwnerId();

        when(jwtServiceUtils.extractSubjectFromToken())
                .thenReturn(tokenEmail);
        when(userExternalServicePort.userHasEmail(restaurantOwnerId, tokenEmail))
                .thenReturn(true);

        InvalidPriceException result = assertThrows(InvalidPriceException.class, () -> dishUseCase.saveDish(dish));

        assertEquals(ExceptionConstants.INVALID_PRICE_EXCEPTION_MESSAGE, result.getMessage());
    }

    @Test
    void updateDish_WhenUpdatePriceIsSuccessful() {
        Long dishId = 1L;
        Integer dishPrice = 20;

        Dish dish = Dish.builder()
                .id(dishId)
                .name("Dish name")
                .category(new Category())
                .description("Dish description")
                .price(5)
                .restaurant(new Restaurant())
                .imageUrl("Image Url")
                .active(true)
                .build();

        Dish savedDish = Dish.builder()
                .id(dishId)
                .name("Dish name")
                .category(new Category())
                .description("Dish description")
                .price(dishPrice)
                .restaurant(new Restaurant())
                .imageUrl("Image Url")
                .active(true)
                .build();

        when(dishPersistencePort.findById(dishId))
                .thenReturn(dish);
        when(dishPersistencePort.save(dish))
                .thenReturn(savedDish);

        Dish result = dishUseCase.updateDish(dishId, dishPrice, null);

        assertEquals(savedDish, result);
        assertEquals(dishPrice, result.getPrice());
    }

    @Test
    void updateDish_WhenUpdatePriceThrowsInvalidPriceException() {
        Long dishId = 1L;
        Integer dishPrice = -20;

        Dish dish = Dish.builder()
                .id(dishId)
                .name("Dish name")
                .category(new Category())
                .description("Dish description")
                .price(5)
                .restaurant(new Restaurant())
                .imageUrl("Image Url")
                .active(true)
                .build();

        when(dishPersistencePort.findById(dishId))
                .thenReturn(dish);

        InvalidPriceException result = assertThrows(InvalidPriceException.class, () -> dishUseCase.updateDish(dishId, dishPrice, null));

        assertEquals(ExceptionConstants.INVALID_PRICE_EXCEPTION_MESSAGE, result.getMessage());
    }

    @Test
    void updateDish_WhenUpdateDescriptionIsSuccessful() {
        Long dishId = 1L;
        String description = "New dish description";

        Dish dish = Dish.builder()
                .id(dishId)
                .name("Dish name")
                .category(new Category())
                .description("Dish description")
                .price(5)
                .restaurant(new Restaurant())
                .imageUrl("Image Url")
                .active(true)
                .build();

        Dish savedDish = Dish.builder()
                .id(dishId)
                .name("Dish name")
                .category(new Category())
                .description(description)
                .price(5)
                .restaurant(new Restaurant())
                .imageUrl("Image Url")
                .active(true)
                .build();

        when(dishPersistencePort.findById(dishId))
                .thenReturn(dish);
        when(dishPersistencePort.save(dish))
                .thenReturn(savedDish);

        Dish result = dishUseCase.updateDish(dishId, null, description);

        assertEquals(savedDish, result);
        assertEquals(description, result.getDescription());
    }
}