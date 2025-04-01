package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;

public interface IEmployeeAssignmentServicePort {

    EmployeeAssignment assignEmployeeToRestaurant(Long employeeId, Long ownerId);
}
