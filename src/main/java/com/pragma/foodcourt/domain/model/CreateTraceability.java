package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateTraceability {

    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private String previousStatus;
    private String newStatus;
    private Long employeeId;
    private String employeeEmail;
}
