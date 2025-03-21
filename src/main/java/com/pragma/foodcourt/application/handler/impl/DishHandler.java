package com.pragma.foodcourt.application.handler.impl;

import com.pragma.foodcourt.application.dto.request.CreateDishRequestDto;
import com.pragma.foodcourt.application.dto.request.UpdateDishRequestDto;
import com.pragma.foodcourt.application.dto.response.DishResponseDto;
import com.pragma.foodcourt.application.handler.IDishHandler;
import com.pragma.foodcourt.domain.api.ICategoryServicePort;
import com.pragma.foodcourt.domain.api.IDishServicePort;
import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.model.Category;
import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DishHandler implements IDishHandler {

    private final IDishServicePort dishServicePort;
    private final ICategoryServicePort categoryServicePort;
    private final IRestaurantServicePort restaurantServicePort;
    private final ModelMapper modelMapper;

    @Override
    public DishResponseDto saveDish(CreateDishRequestDto createDishRequestDto) {

        Category category = categoryServicePort.findByName(createDishRequestDto.getCategoryName());
        Restaurant restaurant = restaurantServicePort.findRestaurantById(createDishRequestDto.getRestaurantId());

        Dish dish = Dish.builder()
                .name(createDishRequestDto.getName())
                .price(createDishRequestDto.getPrice())
                .description(createDishRequestDto.getDescription())
                .imageUrl(createDishRequestDto.getImageUrl())
                .category(category)
                .restaurant(restaurant)
                .active(true)
                .build();

        return modelMapper.map(dishServicePort.saveDish(dish), DishResponseDto.class);
    }

    @Override
    public DishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto) {
        Long dishId = updateDishRequestDto.getDishId();
        Integer price = updateDishRequestDto.getPrice();
        String description = updateDishRequestDto.getDescription();

        Dish updatedDish = dishServicePort.updateDish(dishId, price, description);

        return modelMapper.map(updatedDish, DishResponseDto.class);
    }

    @Override
    public DishResponseDto updateDishStatus(Long dishId, boolean status) {
        Dish savedDish = dishServicePort.updateDishStatus(dishId, status);
        return modelMapper.map(savedDish, DishResponseDto.class);
    }
}
