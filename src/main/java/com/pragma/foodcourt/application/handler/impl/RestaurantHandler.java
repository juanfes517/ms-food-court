package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.application.dto.response.RestaurantBasicInfoResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantResponseDto;
import com.pragma.foodcourt.application.handler.IRestaurantHandler;
import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler {

    private final IRestaurantServicePort restaurantUseCase;
    private final ModelMapper modelMapper;

    @Override
    public RestaurantResponseDto saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        Restaurant mappedRestaurant = modelMapper.map(restaurantRequestDto, Restaurant.class);
        Restaurant savedRestaurant = restaurantUseCase.saveRestaurant(mappedRestaurant);

        return modelMapper.map(savedRestaurant, RestaurantResponseDto.class);
    }

    @Override
    public Page<RestaurantBasicInfoResponseDto> findAllRestaurants(Pageable pageable) {
        return restaurantUseCase.findAllRestaurants(pageable)
                .map(restaurant -> modelMapper.map(restaurant, RestaurantBasicInfoResponseDto.class));
    }
}
