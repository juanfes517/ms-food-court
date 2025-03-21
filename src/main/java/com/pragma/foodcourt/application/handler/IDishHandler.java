package com.pragma.foodcourt.application.handler;

import com.pragma.foodcourt.application.dto.request.CreateDishRequestDto;
import com.pragma.foodcourt.application.dto.request.UpdateDishRequestDto;
import com.pragma.foodcourt.application.dto.response.DishResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDishHandler {

    DishResponseDto saveDish(CreateDishRequestDto createDishRequestDto);

    DishResponseDto updateDish(UpdateDishRequestDto updateDishRequestDto);

    DishResponseDto updateDishStatus(Long dishId, boolean status);

    Page<DishResponseDto> findAllDishes(Pageable pageable, String categoryName, Long restaurantId);
}
