package com.pragma.foodcourt.infrastructure.input.rest;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.application.dto.response.RestaurantResponseDto;
import com.pragma.foodcourt.application.handler.IRestaurantHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final IRestaurantHandler restaurantHandler;

    @PostMapping
    public ResponseEntity<RestaurantResponseDto> saveRestaurant(@RequestBody RestaurantRequestDto restaurantRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurantHandler.saveRestaurant(restaurantRequestDto));
    }
}
