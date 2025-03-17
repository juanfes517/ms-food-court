package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

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
}