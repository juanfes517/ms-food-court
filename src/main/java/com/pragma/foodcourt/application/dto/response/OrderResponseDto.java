package com.pragma.foodcourt.application.dto.response;

import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderResponseDto {

    private Long id;
    private Long customerId;
    private LocalDate date;
    private OrderStatusEnum status;
    private Long chefId;
    private Long restaurantId;
}
