package com.pragma.foodcourt.application.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantEfficiencyResponseDto {

    private Long orderId;
    private double orderDurationInSeconds;
    private String finalStatus;
}
