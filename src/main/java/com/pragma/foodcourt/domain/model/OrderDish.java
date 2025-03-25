package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderDish {

    private Long id;
    private Order order;
    private Dish dish;
    private int quantity;
}
