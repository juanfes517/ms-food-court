package com.pragma.foodcourt.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "cell_phone_number")
    private String cellPhoneNumber;

    @Column(nullable = false, name = "logo_url")
    private String logoUrl;

    @Column(nullable = false, unique = true)
    private String nit;

    @Column(nullable = false, name = "owner_id")
    private Long ownerId;
}
