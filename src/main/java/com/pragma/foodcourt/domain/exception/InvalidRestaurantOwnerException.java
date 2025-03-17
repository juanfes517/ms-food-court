package com.pragma.foodcourt.domain.exception;

public class InvalidRestaurantOwnerException extends RuntimeException {

    public InvalidRestaurantOwnerException(String message) {
        super(message);
    }
}
