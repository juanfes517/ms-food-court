package com.pragma.foodcourt.infrastructure.out.feign.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MessageDto {

    private String customerCellPhoneNumber;
    private String restaurantName;
    private String securityPin;
}
