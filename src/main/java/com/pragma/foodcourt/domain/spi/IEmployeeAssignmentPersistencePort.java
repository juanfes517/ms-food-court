package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;

public interface IEmployeeAssignmentPersistencePort {

    EmployeeAssignment save(EmployeeAssignment employeeAssignment);
}
