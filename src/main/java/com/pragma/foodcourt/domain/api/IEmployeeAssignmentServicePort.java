package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import org.springframework.web.bind.annotation.RequestParam;

public interface IEmployeeAssignmentServicePort {

    EmployeeAssignment assignEmployeeToRestaurant(Long employeeId, Long ownerId);
}
