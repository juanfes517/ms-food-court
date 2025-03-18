package com.pragma.foodcourt.application.dto.request;

import com.pragma.foodcourt.application.helper.constants.DtoConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateDishRequestDto {

    @NotBlank(message = DtoConstants.DISH_NAME_CANNOT_BE_BLANK)
    private String name;

    @NotNull(message = DtoConstants.DISH_PRICE_CANNOT_BE_BLANK)
    private int price;

    @NotBlank(message = DtoConstants.DISH_DESCRIPTION_CANNOT_BE_BLANK)
    private String description;

    @NotBlank(message = DtoConstants.LOGO_URL_CANNOT_BE_BLANK)
    private String imageUrl;

    @NotBlank(message = DtoConstants.CATEGORY_CANNOT_BE_BLANK)
    private String categoryName;

    @NotNull(message = DtoConstants.RESTAURANT_ID_CANNOT_BE_BLANK)
    private Long restaurantId;
}
