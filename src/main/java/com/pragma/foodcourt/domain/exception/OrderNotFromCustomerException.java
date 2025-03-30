package com.pragma.foodcourt.domain.exception;

public class OrderNotFromCustomerException extends RuntimeException {

    public OrderNotFromCustomerException(String message) {
        super(message);
    }
}
