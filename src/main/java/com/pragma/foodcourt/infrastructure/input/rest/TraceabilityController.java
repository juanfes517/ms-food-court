package com.pragma.foodcourt.infrastructure.input.rest;


import com.pragma.foodcourt.application.dto.response.TraceabilityResponseDto;
import com.pragma.foodcourt.application.handler.ITraceabilityHandler;
import com.pragma.foodcourt.infrastructure.helper.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/traceability")
public class TraceabilityController {

    private final ITraceabilityHandler traceabilityHandler;

    @Operation(summary = ApiConstants.GET_TRACEABILITY_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = ApiConstants.OK_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "400", description = ApiConstants.BAD_REQUEST_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content)
    })
    @GetMapping("/order-id")
    public ResponseEntity<List<TraceabilityResponseDto>> getOrderTraceability(@RequestParam Long orderId) {
        return ResponseEntity.ok(traceabilityHandler.getOrderTraceability(orderId));
    }
}
