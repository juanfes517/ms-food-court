package com.pragma.foodcourt.infrastructure.input.rest;

import com.pragma.foodcourt.application.dto.request.OrderRequestDto;
import com.pragma.foodcourt.application.dto.response.OrderResponseDto;
import com.pragma.foodcourt.application.handler.IOrderHandler;
import com.pragma.foodcourt.infrastructure.helper.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final IOrderHandler orderHandler;

    @Operation(summary = ApiConstants.SAVE_ORDER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ApiConstants.OBJECT_CREATED_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "409", description = ApiConstants.CONFLICT_DESCRIPTION, content = @Content)
    })
    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto orderRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderHandler.placeOrder(orderRequest));
    }
}
