package com.pragma.foodcourt.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

    @Test
    void isNumericCellPhoneNumber_ShouldReturnTrue_WhenStartsWithPlusSymbol() {
        Restaurant restaurant = new Restaurant();
        restaurant.setCellPhoneNumber("+573005698325");

        boolean result = restaurant.isNumericCellPhoneNumber();

        assertTrue(result);
    }

    @Test
    void isNumericCellPhoneNumber_ShouldReturnTrue_WhenDoesNotHavePlusSymbol() {
        Restaurant restaurant = new Restaurant();
        restaurant.setCellPhoneNumber("573005698325");

        boolean result = restaurant.isNumericCellPhoneNumber();

        assertTrue(result);
    }

    @Test
    void isNumericCellPhoneNumber_ShouldReturnFalse_WhenItIsNotNumeric() {
        Restaurant restaurant = new Restaurant();
        restaurant.setCellPhoneNumber("asd005698a");

        boolean result = restaurant.isNumericCellPhoneNumber();

        assertFalse(result);
    }

    @Test
    void isNumericNit_ShouldReturnTrue_WhenItIsNumeric() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNit("123456789");

        boolean result = restaurant.isNumericNit();

        assertTrue(result);
    }

    @Test
    void isNumericNit_ShouldReturnFalse_WhenItIsNotNumeric() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNit("asdf1234");

        boolean result = restaurant.isNumericNit();

        assertFalse(result);
    }

    @Test
    void isCellPhoneNumberMax13Chars_ShouldReturnTrue_WhenItHasValidLength() {
        Restaurant restaurant = new Restaurant();
        restaurant.setCellPhoneNumber("+573005698325");

        boolean result = restaurant.isCellPhoneNumberMax13Chars();

        assertTrue(result);
    }

    @Test
    void isCellPhoneNumberMax13Chars_ShouldReturnFalse_WhenItDoesNotHaveValidLength() {
        Restaurant restaurant = new Restaurant();
        restaurant.setCellPhoneNumber("+573005698325321");

        boolean result = restaurant.isCellPhoneNumberMax13Chars();

        assertFalse(result);
    }

    @Test
    void isNameNotNumeric_ShouldReturnTrue_WhenItHasAtLeastOneLetter() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("n123");

        boolean result = restaurant.isNameNotNumeric();

        assertTrue(result);
    }

    @Test
    void isNameNotNumeric_ShouldReturnFalse_WhenItIsNumeric() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("1234567");

        boolean result = restaurant.isNameNotNumeric();

        assertFalse(result);
    }
}