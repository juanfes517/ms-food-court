package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantServicePort {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findRestaurantById(Long id);

    Page<Restaurant> findAllRestaurants(Pageable pageable);
}
