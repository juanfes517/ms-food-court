package com.pragma.foodcourt.infrastructure.input.rest;

import com.pragma.foodcourt.application.dto.request.CreateDishRequestDto;
import com.pragma.foodcourt.application.dto.response.DishResponseDto;
import com.pragma.foodcourt.application.handler.IDishHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dishes")
public class DishController {

    private final IDishHandler dishHandler;

    @PostMapping
    public ResponseEntity<DishResponseDto> saveDish(@Valid @RequestBody CreateDishRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dishHandler.saveDish(requestDto));
    }
}
