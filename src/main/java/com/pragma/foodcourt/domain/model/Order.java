package com.pragma.foodcourt.domain.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Order {

    private Long id;
    private Long customerId;
    private LocalDate date;
    private OrderStatusEnum status;
    private Long chefId;
    private Long restaurantId;
}
