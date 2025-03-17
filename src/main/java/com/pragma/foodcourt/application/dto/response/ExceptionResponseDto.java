package com.pragma.foodcourt.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ExceptionResponseDto {
    private String message;
    private LocalDateTime timestamp;
}
