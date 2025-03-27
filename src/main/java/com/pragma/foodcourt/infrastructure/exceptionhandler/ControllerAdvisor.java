package com.pragma.foodcourt.infrastructure.exceptionhandler;

import com.pragma.foodcourt.application.dto.response.ExceptionResponseDto;
import com.pragma.foodcourt.domain.exception.*;
import com.pragma.foodcourt.infrastructure.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCellPhoneNumberException.class)
    public ExceptionResponseDto handleInvalidCellPhoneNumberException(InvalidCellPhoneNumberException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUserRoleException.class)
    public ExceptionResponseDto handleInvalidUserRoleException(InvalidUserRoleException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NonNumericNitException.class)
    public ExceptionResponseDto handleNonNumericNitException(NonNumericNitException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumericNameException.class)
    public ExceptionResponseDto handleNumericNameException(NumericNameException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDishQuantityException.class)
    public ExceptionResponseDto handleInvalidDishQuantityException(InvalidDishQuantityException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ExceptionResponseDto handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomerHasActiveOrderException.class)
    public ExceptionResponseDto handleCustomerHasActiveOrderException(CustomerHasActiveOrderException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error(message);
        return ExceptionResponseDto.builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionResponseDto handleUserNotFoundException(UserNotFoundException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ExceptionResponseDto handleCategoryNotFoundException(CategoryNotFoundException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ExceptionResponseDto handleRestaurantNotFoundException(RestaurantNotFoundException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ExceptionResponseDto handleOrderNotFoundException(OrderNotFoundException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(InvalidRestaurantOwnerException.class)
    public ExceptionResponseDto handleInvalidRestaurantOwnerException(InvalidRestaurantOwnerException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPriceException.class)
    public ExceptionResponseDto handleInvalidPriceException(InvalidPriceException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDishRestaurantException.class)
    public ExceptionResponseDto handleInvalidDishRestaurantException(InvalidDishRestaurantException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidOrderStatusException.class)
    public ExceptionResponseDto handleInvalidOrderStatusException(InvalidOrderStatusException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DishNotFoundException.class)
    public ExceptionResponseDto handleDishNotFoundException(DishNotFoundException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ExceptionResponseDto handleBadCredentialsException(BadCredentialsException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OrderNotFromEmployeeRestaurantException.class)
    public ExceptionResponseDto handleOrderNotFromEmployeeRestaurantException(OrderNotFromEmployeeRestaurantException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OrderNotAssignedToEmployeeException.class)
    public ExceptionResponseDto handleOrderNotAssignedToEmployeeException(OrderNotAssignedToEmployeeException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NotificationFailedException.class)
    public ExceptionResponseDto handleNotificationFailedException(NotificationFailedException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ExceptionResponseDto handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage());
        return ExceptionResponseDto.builder()
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
