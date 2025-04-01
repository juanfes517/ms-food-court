package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.foodcourt.application.dto.response.TraceabilityResponseDto;
import com.pragma.foodcourt.application.handler.ITraceabilityHandler;
import com.pragma.foodcourt.domain.api.ITraceabilityServicePort;
import com.pragma.foodcourt.domain.model.Traceability;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TraceabilityHandler implements ITraceabilityHandler {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final ModelMapper modelMapper;

    @Override
    public List<TraceabilityResponseDto> getOrderTraceability(Long orderId) {
        List<Traceability> traceabilityList = traceabilityServicePort.getOrderTraceability(orderId);
        return traceabilityList.stream()
                .map(traceability -> modelMapper.map(traceability, TraceabilityResponseDto.class))
                .toList();
    }

    @Override
    public List<RestaurantEfficiencyResponseDto> getRestaurantEfficiency() {
        return traceabilityServicePort.getRestaurantEfficiency().stream()
                .map(restaurantEfficiency -> modelMapper.map(restaurantEfficiency, RestaurantEfficiencyResponseDto.class)).
                toList();
    }
}
