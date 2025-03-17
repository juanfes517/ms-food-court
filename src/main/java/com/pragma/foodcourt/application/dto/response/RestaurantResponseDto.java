package com.pragma.foodcourt.application.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantResponseDto {

    private Long id;
    private String name;
    private String address;
    private String cellPhoneNumber;
    private String logoUrl;
    private String nit;
    private Long ownerId;
}
