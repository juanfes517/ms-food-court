package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.CreateTraceability;
import com.pragma.foodcourt.domain.model.Traceability;

import java.util.List;

public interface ITraceabilityExternalService {

    void createTraceability(CreateTraceability createTraceability);

    List<Traceability> getOrderTraceability(Long orderId);
}
