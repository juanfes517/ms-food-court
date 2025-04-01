package com.pragma.foodcourt.infrastructure.input.rest;

import com.pragma.foodcourt.application.handler.IEmployeeAssignmentHandler;
import com.pragma.foodcourt.infrastructure.helper.constants.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.EMPLOYEE_ASSIGNMENT_CONTROLLER)
public class EmployeeAssignmentController {

    private final IEmployeeAssignmentHandler employeeAssignmentHandler;

    @Operation(summary = ApiConstants.ASSIGN_EMPLOYEE_TO_RESTAURANT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = ApiConstants.OBJECT_CREATED_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "403", description = ApiConstants.FORBIDDEN_DESCRIPTION, content = @Content),
            @ApiResponse(responseCode = "404", description = ApiConstants.NOT_FOUND_DESCRIPTION, content = @Content),
    })
    @PostMapping
    public ResponseEntity<Boolean> assignEmployeeToRestaurant(@RequestParam Long employeeId, @RequestParam Long ownerId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeAssignmentHandler.assignEmployeeToRestaurant(employeeId, ownerId));
    }
}
