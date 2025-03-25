package com.pragma.foodcourt.domain.exception;

public class CustomerHasActiveOrderException extends RuntimeException {

    public CustomerHasActiveOrderException(String message) {
        super(message);
    }
}
