package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.infrastructure.exception.EmployeeAssignmentNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.EmployeeAssignmentEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.EmployeeAssignmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeAssignmentJpaAdapterTest {

    @InjectMocks
    private EmployeeAssignmentJpaAdapter employeeAssignmentJpaAdapter;

    @Mock
    private EmployeeAssignmentRepository employeeAssignmentRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void save_WhenIsSuccessful() {
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(2L)
                .build();

        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(2L)
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(null)
                .employeeId(1L)
                .restaurant(restaurant)
                .build();

        EmployeeAssignmentEntity mappedEmployeeAssignmentEntity = EmployeeAssignmentEntity.builder()
                .id(null)
                .employeeId(1L)
                .restaurant(restaurantEntity)
                .build();

        EmployeeAssignmentEntity savedEmployeeAssignmentEntity = EmployeeAssignmentEntity.builder()
                .id(1L)
                .employeeId(1L)
                .restaurant(restaurantEntity)
                .build();

        EmployeeAssignment mappedEmployeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(1L)
                .restaurant(restaurant)
                .build();

        when(modelMapper.map(employeeAssignment, EmployeeAssignmentEntity.class))
                .thenReturn(mappedEmployeeAssignmentEntity);
        when(employeeAssignmentRepository.save(mappedEmployeeAssignmentEntity))
                .thenReturn(savedEmployeeAssignmentEntity);
        when(modelMapper.map(savedEmployeeAssignmentEntity, EmployeeAssignment.class))
                .thenReturn(mappedEmployeeAssignment);

        EmployeeAssignment result = employeeAssignmentJpaAdapter.save(employeeAssignment);

        assertNotNull(result);
        assertEquals(mappedEmployeeAssignment.getId(), result.getId());
        assertEquals(mappedEmployeeAssignmentEntity.getEmployeeId(), result.getEmployeeId());
        assertEquals(mappedEmployeeAssignmentEntity.getRestaurant().getOwnerId(), result.getRestaurant().getOwnerId());
    }

    @Test
    void findByEmployeeId_WhenIsSuccessful() {
        Long employeeId = 1L;

        EmployeeAssignmentEntity employeeAssignmentEntity = EmployeeAssignmentEntity.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(new RestaurantEntity())
                .build();

        EmployeeAssignment employeeAssignment = EmployeeAssignment.builder()
                .id(1L)
                .employeeId(employeeId)
                .restaurant(new Restaurant())
                .build();

        when(employeeAssignmentRepository.findByEmployeeId(employeeId))
                .thenReturn(Optional.of(employeeAssignmentEntity));
        when(modelMapper.map(employeeAssignmentEntity, EmployeeAssignment.class))
                .thenReturn(employeeAssignment);

        EmployeeAssignment result = employeeAssignmentJpaAdapter.findByEmployeeId(employeeId);

        assertNotNull(result);
        assertEquals(employeeAssignment.getId(), result.getId());
        assertEquals(employeeAssignmentEntity.getEmployeeId(), result.getEmployeeId());
        assertEquals(employeeAssignmentEntity.getRestaurant().getOwnerId(), result.getRestaurant().getOwnerId());
    }

    @Test
    void findByEmployeeId_WhenThrowEmployeeAssignmentNotFoundException() {
        Long employeeId = 1L;

        when(employeeAssignmentRepository.findByEmployeeId(employeeId))
                .thenReturn(Optional.empty());

        EmployeeAssignmentNotFoundException result = assertThrows(EmployeeAssignmentNotFoundException.class, () ->
                employeeAssignmentJpaAdapter.findByEmployeeId(employeeId));

        assertNotNull(result);
        assertEquals(ExceptionConstants.EMPLOYEE_ASSIGNMENT_NOT_FOUND, result.getMessage());
    }
}