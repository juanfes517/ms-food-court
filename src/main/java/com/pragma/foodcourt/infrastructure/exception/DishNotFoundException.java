package com.pragma.foodcourt.infrastructure.exception;

public class DishNotFoundException extends RuntimeException {

    public DishNotFoundException(String message) {
        super(message);
    }
}
