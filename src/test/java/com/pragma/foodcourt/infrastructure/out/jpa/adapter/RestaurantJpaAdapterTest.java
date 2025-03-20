package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.infrastructure.exception.RestaurantNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantJpaAdapterTest {

    @InjectMocks
    private RestaurantJpaAdapter restaurantJpaAdapter;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void save() {
        Restaurant restaurant = Restaurant.builder()
                .id(null)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        RestaurantEntity mappedRestaurantEntity = RestaurantEntity.builder()
                .id(null)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        RestaurantEntity savedRestaurantEntity = RestaurantEntity.builder()
                .id(1L)
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

        when(modelMapper.map(restaurant, RestaurantEntity.class))
                .thenReturn(mappedRestaurantEntity);
        when(restaurantRepository.save(mappedRestaurantEntity))
                .thenReturn(savedRestaurantEntity);
        when(modelMapper.map(savedRestaurantEntity, Restaurant.class))
                .thenReturn(savedRestaurant);

        Restaurant result = restaurantJpaAdapter.save(restaurant);

        assertNotNull(result);
        assertEquals(savedRestaurant.getId(), result.getId());
        assertEquals(savedRestaurant.getName(), result.getName());
        assertEquals(savedRestaurant.getNit(), result.getNit());
        assertEquals(savedRestaurant.getAddress(), result.getAddress());
        assertEquals(savedRestaurant.getCellPhoneNumber(), result.getCellPhoneNumber());
        assertEquals(savedRestaurant.getLogoUrl(), result.getLogoUrl());
        assertEquals(savedRestaurant.getOwnerId(), result.getOwnerId());
    }

    @Test
    void findById_WhenIsSuccessful() {
        Long id = 1L;

        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        Restaurant mappedRestaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(1L)
                .build();

        when(restaurantRepository.findById(id))
                .thenReturn(Optional.of(restaurantEntity));
        when(modelMapper.map(restaurantEntity, Restaurant.class))
                .thenReturn(mappedRestaurant);

        Restaurant result = restaurantJpaAdapter.findById(id);

        assertNotNull(result);
        assertEquals(mappedRestaurant.getId(), result.getId());
        assertEquals(mappedRestaurant.getName(), result.getName());
        assertEquals(mappedRestaurant.getNit(), result.getNit());
        assertEquals(mappedRestaurant.getAddress(), result.getAddress());
        assertEquals(mappedRestaurant.getCellPhoneNumber(), result.getCellPhoneNumber());
        assertEquals(mappedRestaurant.getLogoUrl(), result.getLogoUrl());
        assertEquals(mappedRestaurant.getOwnerId(), result.getOwnerId());
    }

    @Test
    void findById_ShouldThrowRestaurantNotFoundException() {
        Long id = 1L;

        RestaurantNotFoundException restaurantNotFoundException = assertThrows(RestaurantNotFoundException.class, () -> restaurantJpaAdapter.findById(id));

        assertEquals(ExceptionConstants.RESTAURANT_NOT_FOUND, restaurantNotFoundException.getMessage());
    }

    @Test
    void findByOwnerId_WhenIsSuccessful() {
        Long ownerId = 1L;

        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(ownerId)
                .build();

        Restaurant mappedRestaurant = Restaurant.builder()
                .id(1L)
                .name("Restaurant Name")
                .nit("12344567")
                .address("Restaurant Address")
                .cellPhoneNumber("+573005698325")
                .logoUrl("Restaurant Logo")
                .ownerId(ownerId)
                .build();

        when(restaurantRepository.findByOwnerId(ownerId))
                .thenReturn(Optional.of(restaurantEntity));
        when(modelMapper.map(restaurantEntity, Restaurant.class))
                .thenReturn(mappedRestaurant);

        Restaurant result = restaurantJpaAdapter.findByOwnerId(ownerId);

        assertNotNull(result);
        assertEquals(mappedRestaurant.getId(), result.getId());
        assertEquals(mappedRestaurant.getName(), result.getName());
        assertEquals(mappedRestaurant.getNit(), result.getNit());
        assertEquals(mappedRestaurant.getAddress(), result.getAddress());
        assertEquals(mappedRestaurant.getCellPhoneNumber(), result.getCellPhoneNumber());
        assertEquals(mappedRestaurant.getLogoUrl(), result.getLogoUrl());
        assertEquals(mappedRestaurant.getOwnerId(), result.getOwnerId());
    }

    @Test
    void findByOwnerId_ShouldThrowRestaurantNotFoundException() {
        Long ownerId = 1L;

        RestaurantNotFoundException restaurantNotFoundException = assertThrows(RestaurantNotFoundException.class, () -> restaurantJpaAdapter.findByOwnerId(ownerId));

        assertEquals(ExceptionConstants.RESTAURANT_NOT_FOUND, restaurantNotFoundException.getMessage());
    }

}