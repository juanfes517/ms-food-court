package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.EmployeeAssignmentEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.EmployeeAssignmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
}