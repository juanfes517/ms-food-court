package com.pragma.foodcourt.domain.exception;

public class InvalidCellPhoneNumberException extends RuntimeException {

    public InvalidCellPhoneNumberException(String message) {
        super(message);
    }
}
