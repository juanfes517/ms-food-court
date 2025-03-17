package com.pragma.foodcourt.domain.spi;

import com.pragma.foodcourt.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    Restaurant save(Restaurant restaurant);

    Restaurant findById(Long id);
}
