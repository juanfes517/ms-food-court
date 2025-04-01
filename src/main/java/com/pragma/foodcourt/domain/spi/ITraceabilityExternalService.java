package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.*;

import java.util.List;

public interface ITraceabilityExternalService {

    void createTraceability(CreateTraceability createTraceability);

    List<Traceability> getOrderTraceability(Long orderId);

    List<RestaurantEfficiency> getRestaurantEfficiency(List<Long> orderIds);

    List<EmployeeEfficiency> getEmployeeEfficiency(List<Long> employeeIds);
}
