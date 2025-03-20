package com.pragma.foodcourt.domain.api;

import org.springframework.web.bind.annotation.RequestParam;

public interface IEmployeeAssignmentServicePort {

    Boolean assignEmployeeToRestaurant(Long employeeId, Long ownerId);
}
