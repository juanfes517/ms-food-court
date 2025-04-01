package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.api.ITraceabilityServicePort;
import com.pragma.foodcourt.domain.model.Order;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.model.RestaurantEfficiency;
import com.pragma.foodcourt.domain.model.Traceability;
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
}
