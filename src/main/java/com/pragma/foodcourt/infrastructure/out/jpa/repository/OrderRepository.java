package com.pragma.foodcourt.infrastructure.out.jpa.repository;

import com.pragma.foodcourt.infrastructure.out.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long>, JpaSpecificationExecutor<OrderEntity> {

    List<OrderEntity> findAllByCustomerId(Long customerId);

    List<OrderEntity> findAllByRestaurantId(Long restaurantId);
}
