package com.pragma.foodcourt.infrastructure.input.rest;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.application.dto.response.RestaurantBasicInfoResponseDto;
import com.pragma.foodcourt.application.dto.response.RestaurantResponseDto;
import com.pragma.foodcourt.application.handler.IRestaurantHandler;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.RESTAURANT_CONTROLLER)
public class RestaurantController {

    private final IRestaurantHandler restaurantHandler;

    @Operation(summary = ApiConstants.SAVE_RESTAURANT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ApiConstants.OBJECT_CREATED_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "409", description = ApiConstants.CONFLICT_DESCRIPTION, content = @Content)
    })
    @PostMapping
    public ResponseEntity<RestaurantResponseDto> saveRestaurant(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurantHandler.saveRestaurant(restaurantRequestDto));
    }

    @Operation(summary = ApiConstants.GET_ALL_RESTAURANTS_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<RestaurantBasicInfoResponseDto>> getAllRestaurants(@RequestParam Integer page, @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(restaurantHandler.findAllRestaurants(pageable));
    }
}
