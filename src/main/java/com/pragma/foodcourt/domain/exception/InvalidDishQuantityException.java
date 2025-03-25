package com.pragma.foodcourt.domain.exception;

public class InvalidDishQuantityException extends RuntimeException {

    public InvalidDishQuantityException(String message) {
        super(message);
    }
}
