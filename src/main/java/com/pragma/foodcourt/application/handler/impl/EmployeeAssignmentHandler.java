package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.handler.IEmployeeAssignmentHandler;
import com.pragma.foodcourt.domain.api.IEmployeeAssignmentServicePort;
import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeAssignmentHandler implements IEmployeeAssignmentHandler {

    private final IEmployeeAssignmentServicePort employeeAssignmentServicePort;

    @Override
    public boolean assignEmployeeToRestaurant(Long employeeId, Long ownerId) {
        EmployeeAssignment savedEmployeeAssignment = employeeAssignmentServicePort.assignEmployeeToRestaurant(employeeId, ownerId);

        return savedEmployeeAssignment != null;
    }
}
