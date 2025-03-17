package com.pragma.foodcourt.infrastructure.out.jpa.repository;

import com.pragma.foodcourt.infrastructure.out.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
