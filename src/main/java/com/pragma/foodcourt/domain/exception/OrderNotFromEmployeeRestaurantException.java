package com.pragma.foodcourt.domain.exception;

public class OrderNotFromEmployeeRestaurantException extends RuntimeException {

    public OrderNotFromEmployeeRestaurantException(String message) {
        super(message);
    }
}
