package com.pragma.foodcourt.application.handler;

public interface IEmployeeAssignmentHandler {

    boolean assignEmployeeToRestaurant(Long employeeId, Long ownerId);
}
