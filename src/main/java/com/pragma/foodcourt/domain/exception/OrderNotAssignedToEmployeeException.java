package com.pragma.foodcourt.domain.exception;

public class OrderNotAssignedToEmployeeException extends RuntimeException {

    public OrderNotAssignedToEmployeeException(String message) {
        super(message);
    }
}
