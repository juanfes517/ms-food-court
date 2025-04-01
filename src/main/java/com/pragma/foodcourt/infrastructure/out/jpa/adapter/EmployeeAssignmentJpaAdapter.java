package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.IEmployeeAssignmentPersistencePort;
import com.pragma.foodcourt.infrastructure.exception.EmployeeAssignmentNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.EmployeeAssignmentEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.EmployeeAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeAssignmentJpaAdapter implements IEmployeeAssignmentPersistencePort {

    private final EmployeeAssignmentRepository employeeAssignmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public EmployeeAssignment save(EmployeeAssignment employeeAssignment) {
        EmployeeAssignmentEntity mappedEmployeeAssignmentEntity = modelMapper.map(employeeAssignment, EmployeeAssignmentEntity.class);
        EmployeeAssignmentEntity savedEmployeeAssignmentEntity = employeeAssignmentRepository.save(mappedEmployeeAssignmentEntity);

        return modelMapper.map(savedEmployeeAssignmentEntity, EmployeeAssignment.class);
    }

    @Override
    public EmployeeAssignment findByEmployeeId(Long employeeId) {
        EmployeeAssignmentEntity employeeAssignmentEntity = employeeAssignmentRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new EmployeeAssignmentNotFoundException(ExceptionConstants.EMPLOYEE_ASSIGNMENT_NOT_FOUND));

        return modelMapper.map(employeeAssignmentEntity, EmployeeAssignment.class);
    }

    @Override
    public List<EmployeeAssignment> findAllByRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = modelMapper.map(restaurant, RestaurantEntity.class);
        List<EmployeeAssignmentEntity> employeeAssignmentEntities = employeeAssignmentRepository.findAllByRestaurant(restaurantEntity);

        return employeeAssignmentEntities.stream()
                .map(employeeAssignmentEntity ->
                        modelMapper.map(employeeAssignmentEntity, EmployeeAssignment.class))
                .toList();
    }
}
