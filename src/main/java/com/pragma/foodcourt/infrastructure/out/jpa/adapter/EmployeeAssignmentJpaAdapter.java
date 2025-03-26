package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.EmployeeAssignment;
import com.pragma.foodcourt.domain.spi.IEmployeeAssignmentPersistencePort;
import com.pragma.foodcourt.infrastructure.exception.EmployeeAssignmentNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.EmployeeAssignmentEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.EmployeeAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
}
