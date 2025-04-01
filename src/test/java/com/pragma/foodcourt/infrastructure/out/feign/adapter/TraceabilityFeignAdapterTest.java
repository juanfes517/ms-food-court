package com.pragma.foodcourt.infrastructure.out.feign.adapter;

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
}