package com.pragma.foodcourt.application.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class NotifyResponseDto {

    private Long orderId;
    private int securityPin;
}
