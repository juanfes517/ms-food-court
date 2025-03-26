package com.pragma.foodcourt.domain.exception;

public class InvalidDishRestaurantException extends RuntimeException {

    public InvalidDishRestaurantException(String message) {
        super(message);
    }
}
