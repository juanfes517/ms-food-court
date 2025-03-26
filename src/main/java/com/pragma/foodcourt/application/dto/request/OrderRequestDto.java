package com.pragma.foodcourt.application.dto.request;

import com.pragma.foodcourt.application.helper.constants.DtoConstants;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderRequestDto {

    @NotNull(message = DtoConstants.RESTAURANT_ID_CANNOT_BE_BLANK)
    private Long restaurantId;

    @NotNull(message = DtoConstants.LIST_OF_DISHES_CANNOT_BE_BLANK)
    private List<DishRequestDto> dishes;
}
