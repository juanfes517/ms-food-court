package com.pragma.foodcourt.application.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantBasicInfoResponseDto {

    private String name;
    private String logoUrl;
}
