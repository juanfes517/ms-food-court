package com.pragma.foodcourt.domain.exception;

public class NotificationFailedException extends RuntimeException {

    public NotificationFailedException(String message) {
        super(message);
    }
}
