package com.pragma.foodcourt.application.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CategoryResponseDto {

    private Long id;
    private String name;
    private String description;
}
