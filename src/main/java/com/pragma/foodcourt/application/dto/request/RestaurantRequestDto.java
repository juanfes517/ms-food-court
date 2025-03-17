package com.pragma.foodcourt.application.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantRequestDto {

    private String name;
    private String nit;
    private String address;
    private String cellPhoneNumber;
    private String logoUrl;
    private Long ownerId;
}
