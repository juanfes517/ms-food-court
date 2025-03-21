package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantPersistencePort {

    Restaurant save(Restaurant restaurant);

    Restaurant findById(Long id);

    Restaurant findByOwnerId(Long ownerId);

    Page<Restaurant> findAll(Pageable pageable);
}
