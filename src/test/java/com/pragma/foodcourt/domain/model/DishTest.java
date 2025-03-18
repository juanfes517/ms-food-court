package com.pragma.foodcourt.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishTest {

    @Test
    void isValidPrice_ShouldReturnTrue_WhenPriceIsGreaterThanZero() {
        Dish dish = new Dish();
        dish.setPrice(1);

        boolean result = dish.isValidPrice();

        assertTrue(result);
    }

    @Test
    void isValidPrice_ShouldReturnTrue_WhenPriceIsLessThanZero() {
        Dish dish = new Dish();
        dish.setPrice(-1);

        boolean result = dish.isValidPrice();

        assertFalse(result);
    }
}