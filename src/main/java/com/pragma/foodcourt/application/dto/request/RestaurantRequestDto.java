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
public class RestaurantRequestDto {

    @NotBlank(message = DtoConstants.NAME_CANNOT_BE_BLANK)
    private String name;

    @NotBlank(message = DtoConstants.NIT_CANNOT_BE_BLANK)
    private String nit;

    @NotBlank(message = DtoConstants.ADDRESS_CANNOT_BE_BLANK)
    private String address;

    @NotBlank(message = DtoConstants.CELL_PHONE_NUMBER_CANNOT_BE_BLANK)
    private String cellPhoneNumber;

    @NotBlank(message = DtoConstants.LOGO_URL_CANNOT_BE_BLANK)
    private String logoUrl;

    @NotNull(message = DtoConstants.OWNER_ID_CANNOT_BE_BLANK)
    private Long ownerId;
}
