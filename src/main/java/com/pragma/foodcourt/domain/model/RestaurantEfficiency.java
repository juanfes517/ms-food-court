package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantEfficiency {

    private Long orderId;
    private double orderDurationInSeconds;
    private String finalStatus;
}
