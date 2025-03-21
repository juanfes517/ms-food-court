package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.IEmployeeAssignmentPersistencePort;
import com.pragma.foodcourt.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeAssignmentUseCaseTest {

    @InjectMocks
    private EmployeeAssignmentUseCase employeeAssignmentUseCase;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort;

    @Test
    void assignEmployeeToRestaurant_WhenIsSuccessful() {
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

        when(restaurantPersistencePort.findByOwnerId(ownerId))
                .thenReturn(restaurant);
        when(employeeAssignmentPersistencePort.save(any(EmployeeAssignment.class)))
                .thenReturn(savedEmployeeAssignment);

        EmployeeAssignment result = employeeAssignmentUseCase.assignEmployeeToRestaurant(employeeId, ownerId);

        assertNotNull(result);
        assertEquals(savedEmployeeAssignment.getId(), result.getId());
        assertEquals(savedEmployeeAssignment.getEmployeeId(), result.getEmployeeId());
        assertEquals(savedEmployeeAssignment.getRestaurant().getOwnerId(), result.getRestaurant().getOwnerId());

    }
}