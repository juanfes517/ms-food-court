package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.response.EmployeeEfficiencyResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantEfficiencyResponseDto;
import com.pragma.foodcourt.application.dto.response.TraceabilityResponseDto;
import com.pragma.foodcourt.domain.api.ITraceabilityServicePort;
import com.pragma.foodcourt.domain.model.EmployeeEfficiency;
import com.pragma.foodcourt.domain.model.RestaurantEfficiency;
import com.pragma.foodcourt.domain.model.Traceability;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceabilityHandlerTest {

    @InjectMocks
    private TraceabilityHandler traceabilityHandler;

    @Mock
    private ITraceabilityServicePort traceabilityServicePort;

    @Mock
    private ModelMapper modelMapper;


    @Test
    void getOrderTraceability_WhenIsSuccessful() {
        Long orderId = 1L;
        Traceability savedTraceability = Traceability.builder()
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        TraceabilityResponseDto responseTraceability = TraceabilityResponseDto.builder()
                .orderId(1L)
                .customerId(1L)
                .customerEmail("customer@mail.com")
                .date(LocalDateTime.now())
                .previousStatus("previousStatus")
                .newStatus("newStatus")
                .employeeId(2L)
                .employeeEmail("employee@mail.com")
                .build();

        List<Traceability> traceabilityList = List.of(savedTraceability);

        when(traceabilityServicePort.getOrderTraceability(orderId))
                .thenReturn(traceabilityList);
        when(modelMapper.map(savedTraceability, TraceabilityResponseDto.class))
                .thenReturn(responseTraceability);

        List<TraceabilityResponseDto> result = traceabilityHandler.getOrderTraceability(orderId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(responseTraceability.getOrderId(), result.get(0).getOrderId());
        assertEquals(responseTraceability.getCustomerId(), result.get(0).getCustomerId());
        assertEquals(responseTraceability.getCustomerEmail(), result.get(0).getCustomerEmail());
        assertEquals(responseTraceability.getDate(), result.get(0).getDate());
        assertEquals(responseTraceability.getPreviousStatus(), result.get(0).getPreviousStatus());
        assertEquals(responseTraceability.getNewStatus(), result.get(0).getNewStatus());
        assertEquals(responseTraceability.getEmployeeId(), result.get(0).getEmployeeId());
        assertEquals(responseTraceability.getEmployeeEmail(), result.get(0).getEmployeeEmail());

    }

    @Test
    void getRestaurantEfficiency_WhenIsSuccessful() {
        RestaurantEfficiency restaurantEfficiency1 = RestaurantEfficiency.builder()
                .orderId(1L)
                .finalStatus("finalStatus")
                .orderDurationInSeconds(23)
                .build();

        RestaurantEfficiency restaurantEfficiency2 = RestaurantEfficiency.builder()
                .orderId(1L)
                .finalStatus("finalStatus")
                .orderDurationInSeconds(23)
                .build();

        RestaurantEfficiencyResponseDto restaurantEfficiencyDto1 = RestaurantEfficiencyResponseDto.builder()
                .orderId(1L)
                .finalStatus("finalStatus")
                .orderDurationInSeconds(23)
                .build();

        RestaurantEfficiencyResponseDto restaurantEfficiencyDto2 = RestaurantEfficiencyResponseDto.builder()
                .orderId(1L)
                .finalStatus("finalStatus")
                .orderDurationInSeconds(23)
                .build();

        List<RestaurantEfficiency> restaurantEfficiencies = List.of(restaurantEfficiency1, restaurantEfficiency2);

        when(traceabilityServicePort.getRestaurantEfficiency())
                .thenReturn(restaurantEfficiencies);
        when(modelMapper.map(restaurantEfficiency1, RestaurantEfficiencyResponseDto.class))
                .thenReturn(restaurantEfficiencyDto1);
        when(modelMapper.map(restaurantEfficiency2, RestaurantEfficiencyResponseDto.class))
                .thenReturn(restaurantEfficiencyDto2);

        List<RestaurantEfficiencyResponseDto> result = traceabilityHandler.getRestaurantEfficiency();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(restaurantEfficiencyDto1.getOrderId(), result.get(0).getOrderId());
        assertEquals(restaurantEfficiencyDto1.getFinalStatus(), result.get(0).getFinalStatus());
        assertEquals(restaurantEfficiencyDto2.getOrderId(), result.get(1).getOrderId());
        assertEquals(restaurantEfficiencyDto2.getFinalStatus(), result.get(1).getFinalStatus());
    }

    @Test
    void getEmployeeEfficiency_WhenIsSuccessful() {
        EmployeeEfficiency employeeEfficiency1 = EmployeeEfficiency.builder()
                .employeeId(1L)
                .employeeEmail("employee1@mail.com")
                .averageProcessingTimeInSeconds(43)
                .build();

        EmployeeEfficiency employeeEfficiency2 = EmployeeEfficiency.builder()
                .employeeId(2L)
                .employeeEmail("employee2@mail.com")
                .averageProcessingTimeInSeconds(34)
                .build();

        EmployeeEfficiencyResponseDto employeeEfficiencyDto1 = EmployeeEfficiencyResponseDto.builder()
                .employeeId(1L)
                .employeeEmail("employee1@mail.com")
                .averageProcessingTimeInSeconds(43)
                .build();

        EmployeeEfficiencyResponseDto employeeEfficiencyDto2 = EmployeeEfficiencyResponseDto.builder()
                .employeeId(2L)
                .employeeEmail("employee2@mail.com")
                .averageProcessingTimeInSeconds(34)
                .build();

        List<EmployeeEfficiency> employees = List.of(employeeEfficiency1, employeeEfficiency2);

        when(traceabilityServicePort.getEmployeeEfficiency())
                .thenReturn(employees);
        when( modelMapper.map(employeeEfficiency1, EmployeeEfficiencyResponseDto.class))
                .thenReturn(employeeEfficiencyDto1);
        when( modelMapper.map(employeeEfficiency2, EmployeeEfficiencyResponseDto.class))
                .thenReturn(employeeEfficiencyDto2);

        List<EmployeeEfficiencyResponseDto> result = traceabilityHandler.getEmployeeEfficiency();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(employeeEfficiencyDto1.getEmployeeId(), result.get(0).getEmployeeId());
        assertEquals(employeeEfficiencyDto2.getEmployeeId(), result.get(1).getEmployeeId());
        assertEquals(employeeEfficiencyDto1.getEmployeeEmail(), result.get(0).getEmployeeEmail());
        assertEquals(employeeEfficiencyDto2.getEmployeeEmail(), result.get(1).getEmployeeEmail());
    }
}