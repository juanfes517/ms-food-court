package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Restaurant;

import java.util.List;

public interface IEmployeeAssignmentPersistencePort {

    EmployeeAssignment save(EmployeeAssignment employeeAssignment);

    EmployeeAssignment findByEmployeeId(Long employeeId);

    List<EmployeeAssignment> findAllByRestaurant(Restaurant restaurant);
}
