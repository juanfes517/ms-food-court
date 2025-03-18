package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.exception.InvalidCellPhoneNumberException;
import com.pragma.foodcourt.domain.exception.InvalidUserRoleException;
import com.pragma.foodcourt.domain.exception.NonNumericNitException;
import com.pragma.foodcourt.domain.exception.NumericNameException;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourt.domain.spi.IUserExternalServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserExternalServicePort userExternalServicePort;

    @Test
    void saveRestaurant_WhenAllFieldsAreValid() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        Restaurant restaurantSaved = Restaurant.builder()
                .id(1L)
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        when(userExternalServicePort.userHasRole(restaurant.getOwnerId(), "ROLE_OWNER"))
                .thenReturn(true);
        when(restaurantPersistencePort.save(restaurant))
                .thenReturn(restaurantSaved);

        Restaurant result = restaurantUseCase.saveRestaurant(restaurant);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Restaurant", result.getName());
        assertEquals("Address", result.getAddress());
        assertEquals("+573005698325", result.getCellPhoneNumber());
        assertEquals("logoUrl", result.getLogoUrl());
        assertEquals("123456789", result.getNit());
        assertEquals(1L, result.getOwnerId());
    }

    @Test
    void saveRestaurant_WhenThrowInvalidUserRoleException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        when(userExternalServicePort.userHasRole(restaurant.getOwnerId(), "ROLE_OWNER"))
                .thenReturn(false);

        InvalidUserRoleException result = assertThrows(InvalidUserRoleException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(ExceptionConstants.ONLY_OWNER_EXCEPTION_MESSAGE, result.getMessage());
    }

    @Test
    void saveRestaurant_WhenThrowInvalidCellPhoneNumberException_WhenCellPhoneNumberIsNotANumber() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("asdfg56983")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        when(userExternalServicePort.userHasRole(restaurant.getOwnerId(), "ROLE_OWNER"))
                .thenReturn(true);

        InvalidCellPhoneNumberException result = assertThrows(InvalidCellPhoneNumberException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(ExceptionConstants.CELL_PHONE_NUMBER_IS_NOT_A_NUMBER_MESSAGE, result.getMessage());
    }

    @Test
    void saveRestaurant_WhenThrowInvalidCellPhoneNumberException_WhenItDoesNotHaveValidLength() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+57300569832512345")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        when(userExternalServicePort.userHasRole(restaurant.getOwnerId(), "ROLE_OWNER"))
                .thenReturn(true);

        InvalidCellPhoneNumberException result = assertThrows(InvalidCellPhoneNumberException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(ExceptionConstants.CELL_PHONE_NUMBER_LENGTH_EXCEPTION_MESSAGE, result.getMessage());
    }

    @Test
    void saveRestaurant_WhenThrowNonNumericNitException() {
        Restaurant restaurant = Restaurant.builder()
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("qwerty")
                .ownerId(1L)
                .build();

        when(userExternalServicePort.userHasRole(restaurant.getOwnerId(), "ROLE_OWNER"))
                .thenReturn(true);

        NonNumericNitException result = assertThrows(NonNumericNitException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(ExceptionConstants.NON_NUMERIC_NIT_EXCEPTION_MESSAGE, result.getMessage());
    }

    @Test
    void saveRestaurant_WhenThrowNumericNameException() {
        Restaurant restaurant = Restaurant.builder()
                .name("123456")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("123456789")
                .ownerId(1L)
                .build();

        when(userExternalServicePort.userHasRole(restaurant.getOwnerId(), "ROLE_OWNER"))
                .thenReturn(true);

        NumericNameException result = assertThrows(NumericNameException.class, () -> restaurantUseCase.saveRestaurant(restaurant));

        assertEquals(ExceptionConstants.NUMERIC_NAME_EXCEPTION_MESSAGE, result.getMessage());
    }

    @Test
    void findRestaurantById_WhenIsSuccessful() {
        Long id = 1L;

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant")
                .address("Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("logoUrl")
                .nit("1234565")
                .ownerId(2L)
                .build();

        when(restaurantPersistencePort.findById(id))
                .thenReturn(restaurant);

        Restaurant result = restaurantUseCase.findRestaurantById(id);

        assertEquals(restaurant, result);
        assertEquals(restaurant.getId(), result.getId());
        assertEquals(restaurant.getName(), result.getName());
        assertEquals(restaurant.getAddress(), result.getAddress());
        assertEquals(restaurant.getCellPhoneNumber(), result.getCellPhoneNumber());
        assertEquals(restaurant.getLogoUrl(), result.getLogoUrl());
        assertEquals(restaurant.getNit(), result.getNit());
        assertEquals(restaurant.getOwnerId(), result.getOwnerId());
    }
}