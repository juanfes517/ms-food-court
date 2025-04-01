package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.RestaurantEfficiency;
import com.pragma.foodcourt.domain.model.Traceability;

import java.util.List;

public interface ITraceabilityServicePort {

    List<Traceability> getOrderTraceability(Long orderId);

    List<RestaurantEfficiency> getRestaurantEfficiency();
}
