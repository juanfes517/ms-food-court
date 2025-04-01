package com.pragma.foodcourt.infrastructure.input.rest;

import com.pragma.foodcourt.application.dto.request.CreateDishRequestDto;
import com.pragma.foodcourt.application.dto.request.UpdateDishRequestDto;
import com.pragma.foodcourt.application.dto.response.DishResponseDto;
import com.pragma.foodcourt.application.handler.IDishHandler;
import com.pragma.foodcourt.infrastructure.helper.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.DISH_CONTROLLER)
public class DishController {

    private final IDishHandler dishHandler;

    @Operation(summary = ApiConstants.SAVE_DISH_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ApiConstants.OBJECT_CREATED_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "409", description = ApiConstants.CONFLICT_DESCRIPTION, content = @Content)
    })
    @PostMapping
    public ResponseEntity<DishResponseDto> saveDish(@Valid @RequestBody CreateDishRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dishHandler.saveDish(requestDto));
    }

    @Operation(summary = ApiConstants.UPDATE_DISH_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "409", description = ApiConstants.CONFLICT_DESCRIPTION, content = @Content)
    })
    @PatchMapping
    public ResponseEntity<DishResponseDto> updateDish(@Valid @RequestBody UpdateDishRequestDto requestDto) {
        return ResponseEntity.ok(dishHandler.updateDish(requestDto));
    }

    @Operation(summary = ApiConstants.UPDATE_DISH_STATUS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content)
    })
    @PatchMapping(ApiConstants.UPDATE_DISH_STATUS_ENDPOINT)
    public ResponseEntity<DishResponseDto> updateDishStatus(@PathVariable("dish-id") Long dishId, @RequestParam boolean status) {
        return ResponseEntity.ok(dishHandler.updateDishStatus(dishId, status));
    }

    @Operation(summary = ApiConstants.GET_ALL_DISHES_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<DishResponseDto>> findAllDishes(
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam Long restaurantId,
            @RequestParam(required = false) String categoryName) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(dishHandler.findAllDishes(pageable, categoryName, restaurantId));
    }
}
