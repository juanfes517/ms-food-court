package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private String cellPhoneNumber;
    private String logoUrl;
    private String nit;
    private Long ownerId;

}
