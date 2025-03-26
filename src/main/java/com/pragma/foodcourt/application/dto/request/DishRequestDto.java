package com.pragma.foodcourt.application.dto.request;

import com.pragma.foodcourt.application.helper.constants.DtoConstants;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DishRequestDto {

    @NotNull(message = DtoConstants.DISH_ID_CANNOT_BE_BLANK)
    private Long dishId;

    @NotNull(message = DtoConstants.QUANTITY_CANNOT_BE_BLANK)
    private Integer quantity;
}
