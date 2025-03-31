package com.pragma.foodcourt.infrastructure.out.feign.adapter;

import com.pragma.foodcourt.domain.model.CreateTraceability;
import com.pragma.foodcourt.domain.spi.ITraceabilityExternalService;
import com.pragma.foodcourt.infrastructure.out.feign.client.TraceabilityFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TraceabilityFeignAdapter implements ITraceabilityExternalService {

    private final TraceabilityFeignClient traceabilityFeignClient;

    @Override
    public void createTraceability(CreateTraceability createTraceability) {
        traceabilityFeignClient.createTraceability(createTraceability);
    }
}
