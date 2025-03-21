package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmployeeAssignment {

    private Long id;
    private Long employeeId;
    private Restaurant restaurant;
}
