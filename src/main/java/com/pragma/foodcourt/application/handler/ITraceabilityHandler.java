package com.pragma.foodcourt.application.handler;

import com.pragma.foodcourt.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.foodcourt.application.dto.response.TraceabilityResponseDto;

import java.util.List;

public interface ITraceabilityHandler {

    List<TraceabilityResponseDto> getOrderTraceability(Long orderId);

    List<RestaurantEfficiencyResponseDto> getRestaurantEfficiency();

    List<EmployeeEfficiencyResponseDto> getEmployeeEfficiency();
}
