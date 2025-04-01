package com.pragma.foodcourt.infrastructure.out.jpa.repository;

import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.EmployeeAssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeAssignmentRepository extends JpaRepository<EmployeeAssignmentEntity, Long> {

    Optional<EmployeeAssignmentEntity> findByEmployeeId(Long employeeId);

    List<EmployeeAssignmentEntity> findAllByRestaurant(Restaurant restaurant);
}
