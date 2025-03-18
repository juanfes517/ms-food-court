package com.pragma.foodcourt.domain.api;

import com.pragma.foodcourt.domain.model.Restaurant;

public interface IRestaurantServicePort {

    Restaurant saveRestaurant(Restaurant restaurant);

    Restaurant findRestaurantById(Long id);
}
