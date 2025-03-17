package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Category {
    private Long id;
    private String name;
    private String description;
}
