package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.IEmployeeAssignmentServicePort;
import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.IEmployeeAssignmentPersistencePort;
import com.pragma.foodcourt.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmployeeAssignmentUseCase implements IEmployeeAssignmentServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort;

    @Override
    public EmployeeAssignment assignEmployeeToRestaurant(Long employeeId, Long ownerId) {

        Restaurant restaurant = restaurantPersistencePort.findByOwnerId(ownerId);

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .employeeId(employeeId)
                .restaurant(restaurant)
                .build();

        return employeeAssignmentPersistencePort.save(employeeAssignment);
    }
}
