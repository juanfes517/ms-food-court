package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.ITraceabilityServicePort;
import com.pragma.foodcourt.domain.model.*;
import com.pragma.foodcourt.domain.spi.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityExternalService traceabilityExternalService;
    private final IUserExternalServicePort userExternalService;
    private final IJwtSecurityServicePort jwtSecurityService;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort;

    @Override
    public List<Traceability> getOrderTraceability(Long orderId) {
        return traceabilityExternalService.getOrderTraceability(orderId);
    }

    @Override
    public List<RestaurantEfficiency> getRestaurantEfficiency() {
        String tokenEmail = jwtSecurityService.getSubject();
        Long ownerId = userExternalService.getUserIdByEmail(tokenEmail);
        Restaurant restaurant = restaurantPersistencePort.findByOwnerId(ownerId);

        List<Long> orderIds = orderPersistencePort.findAllByRestaurantId(restaurant.getId()).stream()
                .map(Order::getId)
                .toList();

        return traceabilityExternalService.getRestaurantEfficiency(orderIds);
    }

    @Override
    public List<EmployeeEfficiency> getEmployeeEfficiency() {
        String tokenEmail = jwtSecurityService.getSubject();
        Long ownerId = userExternalService.getUserIdByEmail(tokenEmail);
        Restaurant restaurant = restaurantPersistencePort.findByOwnerId(ownerId);

        List<Long> employeeIds = employeeAssignmentPersistencePort.findAllByRestaurant(restaurant).stream()
                .map(EmployeeAssignment::getEmployeeId)
                .toList();

        return traceabilityExternalService.getEmployeeEfficiency(employeeIds);
    }
}
