package com.pragma.foodcourt.infrastructure.out.feign.adapter;

import com.pragma.foodcourt.domain.model.*;
import com.pragma.foodcourt.domain.spi.ITraceabilityExternalService;
import com.pragma.foodcourt.infrastructure.out.feign.client.TraceabilityFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TraceabilityFeignAdapter implements ITraceabilityExternalService {

    private final TraceabilityFeignClient traceabilityFeignClient;

    @Override
    public void createTraceability(CreateTraceability createTraceability) {
        traceabilityFeignClient.createTraceability(createTraceability);
    }

    @Override
    public List<Traceability> getOrderTraceability(Long orderId) {
        return traceabilityFeignClient.getOrderTraceability(orderId);
    }

    @Override
    public List<RestaurantEfficiency> getRestaurantEfficiency(List<Long> orderIds) {
        return traceabilityFeignClient.getRestaurantEfficiency(orderIds);
    }

    @Override
    public List<EmployeeEfficiency> getEmployeeEfficiency(List<Long> employeeIds) {
        return traceabilityFeignClient.getEmployeeEfficiency(employeeIds);
    }
}
