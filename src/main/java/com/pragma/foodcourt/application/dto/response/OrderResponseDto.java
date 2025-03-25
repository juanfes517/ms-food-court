package com.pragma.foodcourt.application.dto.response;

import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderResponseDto {

    private Long id;
    private LocalDate date;
    private OrderStatusEnum status;
    private List<DishOrderResponseDto> dishes;
}
