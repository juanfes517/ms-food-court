package com.pragma.foodcourt.infrastructure.out.feign.adapter;

import com.pragma.foodcourt.domain.model.EmployeeEfficiency;
import com.pragma.foodcourt.domain.model.RestaurantEfficiency;
import com.pragma.foodcourt.domain.model.Traceability;
import com.pragma.foodcourt.infrastructure.out.feign.client.TraceabilityFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceabilityFeignAdapterTest {

    @InjectMocks
    private TraceabilityFeignAdapter traceabilityFeignAdapter;

    @Mock
    private TraceabilityFeignClient traceabilityFeignClient;

    @Test
    void getOrderTraceability_whenIsSuccessful() {
        Long orderId = 1L;

        Traceability traceability = Traceability.builder()
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("PENDING")
                .newStatus("PREPARING")
                .employeeId(2L)
                .employeeEmail("employee1@mail.com")
                .build();

        List<Traceability> traceabilityList = List.of(traceability);

        when(traceabilityFeignClient.getOrderTraceability(orderId))
                .thenReturn(traceabilityList);

        List<Traceability> result = traceabilityFeignAdapter.getOrderTraceability(orderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(traceability.getOrderId(), result.get(0).getOrderId());
        assertEquals(traceability.getCustomerId(), result.get(0).getCustomerId());
        assertEquals(traceability.getCustomerEmail(), result.get(0).getCustomerEmail());
        assertEquals(traceability.getDate(), result.get(0).getDate());
        assertEquals(traceability.getPreviousStatus(), result.get(0).getPreviousStatus());
        assertEquals(traceability.getNewStatus(), result.get(0).getNewStatus());
        assertEquals(traceability.getEmployeeId(), result.get(0).getEmployeeId());
        assertEquals(traceability.getEmployeeEmail(), result.get(0).getEmployeeEmail());
    }

    @Test
    void getRestaurantEfficiency_WhenIsSuccessful() {
        List<Long> orderIds = List.of(1L, 2L);

        RestaurantEfficiency restaurantEfficiency1 = RestaurantEfficiency.builder()
                .orderId(orderIds.get(0))
                .orderDurationInSeconds(23)
                .finalStatus("FINAL_STATUS")
                .build();

        RestaurantEfficiency restaurantEfficiency2 = RestaurantEfficiency.builder()
                .orderId(orderIds.get(1))
                .orderDurationInSeconds(23)
                .finalStatus("FINAL_STATUS")
                .build();

        List<RestaurantEfficiency> restaurantEfficiencies = List.of(restaurantEfficiency1, restaurantEfficiency2);

        when(traceabilityFeignClient.getRestaurantEfficiency(orderIds))
                .thenReturn(restaurantEfficiencies);

        List<RestaurantEfficiency> result = traceabilityFeignAdapter.getRestaurantEfficiency(orderIds);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(restaurantEfficiency1.getOrderId(), result.get(0).getOrderId());
        assertEquals(restaurantEfficiency1.getFinalStatus(), result.get(0).getFinalStatus());
        assertEquals(restaurantEfficiency2.getOrderId(), result.get(1).getOrderId());
        assertEquals(restaurantEfficiency2.getFinalStatus(), result.get(1).getFinalStatus());
    }

    @Test
    void getEmployeeEfficiency_WhenIsSuccessful() {
        List<Long> employeeIds = List.of(101L, 102L);

        EmployeeEfficiency efficiency1 = EmployeeEfficiency.builder()
                .employeeId(employeeIds.get(0))
                .employeeEmail("employee1@example.com")
                .averageProcessingTimeInSeconds(120.5)
                .build();

        EmployeeEfficiency efficiency2 = EmployeeEfficiency.builder()
                .employeeId(employeeIds.get(1))
                .employeeEmail("employee2@example.com")
                .averageProcessingTimeInSeconds(95.0)
                .build();

        List<EmployeeEfficiency> employeeEfficiencies = List.of(efficiency1, efficiency2);

        when(traceabilityFeignClient.getEmployeeEfficiency(employeeIds))
                .thenReturn(employeeEfficiencies);

        List<EmployeeEfficiency> result = traceabilityFeignAdapter.getEmployeeEfficiency(employeeIds);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(efficiency1.getEmployeeId(), result.get(0).getEmployeeId());
        assertEquals(efficiency1.getEmployeeEmail(), result.get(0).getEmployeeEmail());
        assertEquals(efficiency1.getAverageProcessingTimeInSeconds(), result.get(0).getAverageProcessingTimeInSeconds());
        assertEquals(efficiency2.getEmployeeId(), result.get(1).getEmployeeId());
        assertEquals(efficiency2.getEmployeeEmail(), result.get(1).getEmployeeEmail());
        assertEquals(efficiency2.getAverageProcessingTimeInSeconds(), result.get(1).getAverageProcessingTimeInSeconds());
    }
}