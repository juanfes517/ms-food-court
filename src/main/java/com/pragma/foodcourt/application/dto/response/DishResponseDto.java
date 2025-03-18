package com.pragma.foodcourt.application.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class DishResponseDto {

    private Long id;
    private String name;
    private CategoryResponseDto category;
    private String description;
    private int price;
    private RestaurantResponseDto restaurant;
    private String imageUrl;
    private boolean active;
}
