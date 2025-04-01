package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.model.*;
import com.pragma.foodcourt.domain.spi.*;
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
class TraceabilityUseCaseTest {

    @InjectMocks
    private TraceabilityUseCase traceabilityUseCase;

    @Mock
    private ITraceabilityExternalService traceabilityExternalService;

    @Mock
    private IUserExternalServicePort userExternalService;

    @Mock
    private IJwtSecurityServicePort jwtSecurityService;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort;

    @Test
    void getOrderTraceability_WhenIsSuccessful() {
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

        when(traceabilityExternalService.getOrderTraceability(orderId))
                .thenReturn(traceabilityList);

        List<Traceability> result = traceabilityUseCase.getOrderTraceability(orderId);

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
        String tokenEmail = "owner@mail.com";
        Long ownerId = 1L;

        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .build();

        Order order1 = Order.builder()
                .id(1L)
                .build();

        Order order2 = Order.builder()
                .id(2L)
                .build();

        List<Order> orderList = List.of(order1, order2);
        List<Long> orderIds = List.of(order1.getId(), order2.getId());

        RestaurantEfficiency restaurantEfficiency1 = RestaurantEfficiency.builder()
                .orderId(1L)
                .finalStatus("status")
                .orderDurationInSeconds(32)
                .build();

        RestaurantEfficiency restaurantEfficiency2 = RestaurantEfficiency.builder()
                .orderId(2L)
                .finalStatus("status")
                .orderDurationInSeconds(23)
                .build();

        List<RestaurantEfficiency> restaurantEfficiencies = List.of(restaurantEfficiency1, restaurantEfficiency2);

        when(jwtSecurityService.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalService.getUserIdByEmail(tokenEmail))
                .thenReturn(ownerId);
        when(restaurantPersistencePort.findByOwnerId(ownerId))
                .thenReturn(restaurant);
        when(orderPersistencePort.findAllByRestaurantId(restaurant.getId()))
                .thenReturn(orderList);
        when(traceabilityExternalService.getRestaurantEfficiency(orderIds))
                .thenReturn(restaurantEfficiencies);

        List<RestaurantEfficiency> result = traceabilityUseCase.getRestaurantEfficiency();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(restaurantEfficiency1.getOrderId(), result.get(0).getOrderId());
        assertEquals(restaurantEfficiency2.getOrderId(), result.get(1).getOrderId());
        assertEquals(restaurantEfficiency1.getFinalStatus(), result.get(0).getFinalStatus());
        assertEquals(restaurantEfficiency2.getFinalStatus(), result.get(1).getFinalStatus());
    }

    @Test
    void getEmployeeEfficiency_WhenIsSuccessful() {
        String tokenEmail = "owner@mail.com";
        Long ownerId = 1L;
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .build();

        EmployeeAssignment employeeAssignment1 = EmployeeAssignment.builder()
                .employeeId(101L)
                .restaurant(restaurant)
                .build();

        EmployeeAssignment employeeAssignment2 = EmployeeAssignment.builder()
                .employeeId(102L)
                .restaurant(restaurant)
                .build();

        List<EmployeeAssignment> employeeAssignments = List.of(employeeAssignment1, employeeAssignment2);
        List<Long> employeeIds = employeeAssignments.stream()
                .map(EmployeeAssignment::getEmployeeId)
                .toList();

        EmployeeEfficiency efficiency1 = EmployeeEfficiency.builder()
                .employeeId(101L)
                .employeeEmail("employee1@mail.com")
                .averageProcessingTimeInSeconds(45.5)
                .build();

        EmployeeEfficiency efficiency2 = EmployeeEfficiency.builder()
                .employeeId(102L)
                .employeeEmail("employee2@mail.com")
                .averageProcessingTimeInSeconds(38.2)
                .build();

        List<EmployeeEfficiency> employeeEfficiencies = List.of(efficiency1, efficiency2);

        when(jwtSecurityService.getSubject())
                .thenReturn(tokenEmail);
        when(userExternalService.getUserIdByEmail(tokenEmail))
                .thenReturn(ownerId);
        when(restaurantPersistencePort.findByOwnerId(ownerId))
                .thenReturn(restaurant);
        when(employeeAssignmentPersistencePort.findAllByRestaurant(restaurant))
                .thenReturn(employeeAssignments);
        when(traceabilityExternalService.getEmployeeEfficiency(employeeIds))
                .thenReturn(employeeEfficiencies);

        List<EmployeeEfficiency> result = traceabilityUseCase.getEmployeeEfficiency();

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