package com.pragma.foodcourt.infrastructure.exception;

public class EmployeeAssignmentNotFoundException extends RuntimeException {

    public EmployeeAssignmentNotFoundException(String message) {
        super(message);
    }
}
