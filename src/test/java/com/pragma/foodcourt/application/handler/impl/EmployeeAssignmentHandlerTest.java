package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.domain.api.IEmployeeAssignmentServicePort;
import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeAssignmentHandlerTest {

    @InjectMocks
    private EmployeeAssignmentHandler employeeAssignmentHandler;

    @Mock
    private IEmployeeAssignmentServicePort employeeAssignmentServicePort;

    @Test
    void assignEmployeeToRestaurant_WhenItReturnsTrue() {
        Long employeeId = 1L;
        Long ownerId = 2L;

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(ownerId)
                .build();

        EmployeeAssignment savedEmployeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(restaurant)
                .build();

        when(employeeAssignmentServicePort.assignEmployeeToRestaurant(employeeId, ownerId))
                .thenReturn(savedEmployeeAssignment);

        boolean result = employeeAssignmentHandler.assignEmployeeToRestaurant(employeeId, ownerId);

        assertTrue(result);
    }

    @Test
    void assignEmployeeToRestaurant_WhenItReturnsFalse() {
        Long employeeId = 1L;
        Long ownerId = 2L;

        when(employeeAssignmentServicePort.assignEmployeeToRestaurant(employeeId, ownerId))
                .thenReturn(null);

        boolean result = employeeAssignmentHandler.assignEmployeeToRestaurant(employeeId, ownerId);

        assertFalse(result);
    }
}