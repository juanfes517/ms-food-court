package com.pragma.foodcourt.infrastructure.exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
