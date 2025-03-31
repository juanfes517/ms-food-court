package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.CreateTraceability;

public interface ITraceabilityExternalService {

    void createTraceability(CreateTraceability createTraceability);
}
