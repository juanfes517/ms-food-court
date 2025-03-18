package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Dish {

    private Long id;
    private String name;
    private Category category;
    private String description;
    private int price;
    private Restaurant restaurant;
    private String imageUrl;
    private boolean active;

    public boolean isValidPrice() {
        return price > 0;
    }
}
