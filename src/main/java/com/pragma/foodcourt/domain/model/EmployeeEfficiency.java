package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmployeeEfficiency {

    private Long employeeId;
    private String employeeEmail;
    private double averageProcessingTimeInSeconds;
}
