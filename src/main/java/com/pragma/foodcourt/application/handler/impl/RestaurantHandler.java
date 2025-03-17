package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.application.dto.response.RestaurantResponseDto;
import com.pragma.foodcourt.application.handler.IRestaurantHandler;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
public class RestaurantHandler implements IRestaurantHandler {

    private final RestaurantUseCase restaurantUseCase;
    private final ModelMapper modelMapper;

    @Override
    public RestaurantResponseDto saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        Restaurant mappedRestaurant = modelMapper.map(restaurantRequestDto, Restaurant.class);
        Restaurant savedRestaurant = restaurantUseCase.saveRestaurant(mappedRestaurant);

        return modelMapper.map(savedRestaurant, RestaurantResponseDto.class);
    }
}
