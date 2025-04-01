package com.pragma.foodcourt.infrastructure.input.rest;

import com.pragma.foodcourt.application.dto.request.OrderRequestDto;
import com.pragma.foodcourt.application.dto.response.NotifyResponseDto;
import com.pragma.foodcourt.application.dto.response.OrderResponseDto;
import com.pragma.foodcourt.application.dto.response.OrderWithDishesResponseDto;
import com.pragma.foodcourt.application.handler.IOrderHandler;
import com.pragma.foodcourt.domain.model.OrderStatusEnum;
import com.pragma.foodcourt.infrastructure.helper.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.ORDER_CONTROLLER)
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
    public ResponseEntity<OrderWithDishesResponseDto> placeOrder(@RequestBody OrderRequestDto orderRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderHandler.placeOrder(orderRequest));
    }

    @Operation(summary = ApiConstants.GET_ALL_ORDER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrders(
            @RequestParam Integer page,
            @RequestParam Integer pageSize,
            @RequestParam OrderStatusEnum status) {
        return ResponseEntity.ok(orderHandler.getAllOrders(page, pageSize, status));
    }

    @Operation(summary = ApiConstants.ASSIGN_ORDER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content)
    })
    @PatchMapping(ApiConstants.ASSIGN_ORDER_ENDPOINT)
    public ResponseEntity<OrderResponseDto> assignOrder(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(orderHandler.assignOrder(orderId));
    }

    @Operation(summary = ApiConstants.MARK_ORDER_AS_READY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "500", description = ApiConstants.INTERNAL_SERVER_ERROR_DESCRIPTION, content = @Content)
    })
    @PatchMapping(ApiConstants.MARK_ORDER_READY_ENDPOINT)
    public ResponseEntity<NotifyResponseDto> markOrderReady(@PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(orderHandler.markOrderReady(orderId));
    }

    @Operation(summary = ApiConstants.MARK_ORDER_AS_DELIVERED_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "500", description = ApiConstants.INTERNAL_SERVER_ERROR_DESCRIPTION, content = @Content)
    })
    @PatchMapping(ApiConstants.MARK_ORDER_DELIVERED_ENDPOINT)
    public ResponseEntity<OrderResponseDto> markOrderDelivered(@PathVariable("order-id") Long orderId, @RequestParam String securityPin) {
        return ResponseEntity.ok(orderHandler.markOrderDelivered(orderId, securityPin));
    }

    @Operation(summary = ApiConstants.CANCEL_ORDER_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "500", description = ApiConstants.INTERNAL_SERVER_ERROR_DESCRIPTION, content = @Content)
    })
    @PatchMapping(ApiConstants.CANCEL_ORDER_ENDPOINT)
    public ResponseEntity<OrderResponseDto> markOrderDelivered(
            @PathVariable("order-id") Long orderId) {
        return ResponseEntity.ok(orderHandler.cancelOrder(orderId));
    }
}
