package com.pragma.foodcourt.application.dto.request;

import com.pragma.foodcourt.application.helper.constants.DtoConstants;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UpdateDishRequestDto {

    @NotNull(message = DtoConstants.DISH_ID_CANNOT_BE_BLANK)
    private Long dishId;

    private Integer price;
    private String description;
}
