package com.pragma.foodcourt.application.handler;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.application.dto.response.RestaurantBasicInfoResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRestaurantHandler {

    RestaurantResponseDto saveRestaurant(RestaurantRequestDto restaurantRequestDto);

    Page<RestaurantBasicInfoResponseDto> findAllRestaurants(Pageable pageable);
}
