package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.ITraceabilityServicePort;
import com.pragma.foodcourt.domain.model.Traceability;
import com.pragma.foodcourt.domain.spi.ITraceabilityExternalService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TraceabilityUseCase implements ITraceabilityServicePort {

    private final ITraceabilityExternalService traceabilityExternalService;

    @Override
    public List<Traceability> getOrderTraceability(Long orderId) {
        return traceabilityExternalService.getOrderTraceability(orderId);
    }
}
