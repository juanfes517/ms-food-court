package com.pragma.foodcourt.domain.exception;

public class InvalidSecurityPinException extends RuntimeException{

    public InvalidSecurityPinException(String message) {
        super(message);
    }
}
