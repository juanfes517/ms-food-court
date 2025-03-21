package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourt.infrastructure.exception.RestaurantNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity mappedRestaurantEntity = modelMapper.map(restaurant, RestaurantEntity.class);
        RestaurantEntity savedRestaurantEntity = restaurantRepository.save(mappedRestaurantEntity);

        return modelMapper.map(savedRestaurantEntity, Restaurant.class);
    }

    @Override
    public Restaurant findById(Long id) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(ExceptionConstants.RESTAURANT_NOT_FOUND));

        return modelMapper.map(restaurantEntity, Restaurant.class);
    }

    @Override
    public Restaurant findByOwnerId(Long ownerId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new RestaurantNotFoundException(ExceptionConstants.RESTAURANT_NOT_FOUND));

        return modelMapper.map(restaurantEntity, Restaurant.class);
    }
}
