package com.pragma.foodcourt.application.handler;

import com.pragma.foodcourt.application.dto.response.TraceabilityResponseDto;

import java.util.List;

public interface ITraceabilityHandler {

    List<TraceabilityResponseDto> getOrderTraceability(Long orderId);
}
