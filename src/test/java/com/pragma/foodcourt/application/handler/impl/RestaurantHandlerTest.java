package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.application.dto.response.RestaurantBasicInfoResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantResponseDto;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.usecase.RestaurantUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerTest {

    @InjectMocks
    private RestaurantHandler restaurantHandler;

    @Mock
    private RestaurantUseCase restaurantUseCase;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void saveRestaurant() {
        RestaurantRequestDto restaurantRequestDto = RestaurantRequestDto.builder()
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        Restaurant mappedRestaurant = Restaurant.builder()
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        Restaurant savedRestaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        RestaurantResponseDto restaurantResponseDto = RestaurantResponseDto.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        when(modelMapper.map(restaurantRequestDto, Restaurant.class))
                .thenReturn(mappedRestaurant);
        when(restaurantUseCase.saveRestaurant(mappedRestaurant))
                .thenReturn(savedRestaurant);
        when(modelMapper.map(savedRestaurant, RestaurantResponseDto.class))
                .thenReturn(restaurantResponseDto);

        RestaurantResponseDto result = restaurantHandler.saveRestaurant(restaurantRequestDto);

        assertNotNull(result);
        assertEquals(restaurantResponseDto.getId(), result.getId());
        assertEquals(restaurantResponseDto.getName(), result.getName());
        assertEquals(restaurantResponseDto.getNit(), result.getNit());
        assertEquals(restaurantResponseDto.getAddress(), result.getAddress());
        assertEquals(restaurantResponseDto.getCellPhoneNumber(), result.getCellPhoneNumber());
        assertEquals(restaurantResponseDto.getLogoUrl(), result.getLogoUrl());
        assertEquals(restaurantResponseDto.getOwnerId(), result.getOwnerId());
    }

    @Test
    void findAllRestaurants_whenIsSuccessful() {
        Restaurant restaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        RestaurantBasicInfoResponseDto restaurantResponse = RestaurantBasicInfoResponseDto.builder()
                .name("Restaurant Name")
                .logoUrl("Restaurant Logo")
                .build();

        List<Restaurant> restaurantList = List.of(restaurant);
        Page<Restaurant> restaurantPage = new PageImpl<>(restaurantList);
        Pageable pageable = PageRequest.of(0, 10);

        when(restaurantUseCase.findAllRestaurants(pageable))
                .thenReturn(restaurantPage);
        when(modelMapper.map(restaurant, RestaurantBasicInfoResponseDto.class))
                .thenReturn(restaurantResponse);

        Page<RestaurantBasicInfoResponseDto> result = restaurantHandler.findAllRestaurants(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(restaurantResponse.getName(), result.getContent().get(0).getName());
        assertEquals(restaurantResponse.getLogoUrl(), result.getContent().get(0).getLogoUrl());
    }
}