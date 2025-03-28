package com.pragma.foodcourt.infrastructure.out.jpa.entity;

import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "customer_id")
    private Long customerId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private OrderStatusEnum status;

    @Column(name = "chef_id")
    private Long chefId;

    @Column(nullable = false, name = "restaurant_id")
    private Long restaurantId;

    @Column(name = "security_pin")
    private String securityPin;
}
