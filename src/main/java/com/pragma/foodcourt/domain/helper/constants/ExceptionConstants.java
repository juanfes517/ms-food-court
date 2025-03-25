package com.pragma.foodcourt.domain.helper.constants;

public class ExceptionConstants {

    private ExceptionConstants() {
    }

    public static final String ONLY_OWNER_EXCEPTION_MESSAGE = "Only users with the owner role can have restaurants";
    public static final String CELL_PHONE_NUMBER_IS_NOT_A_NUMBER_MESSAGE = "The cell phone number is not a number";
    public static final String CELL_PHONE_NUMBER_LENGTH_EXCEPTION_MESSAGE = "The cell phone number is no valid. Must have a maximum of 13 characters";
    public static final String NON_NUMERIC_NIT_EXCEPTION_MESSAGE = "The NIT number must only contain numerical values";
    public static final String NUMERIC_NAME_EXCEPTION_MESSAGE = "The restaurant name should not contain only numerical values.";
    public static final String INVALID_PRICE_EXCEPTION_MESSAGE = "The price must be a positive number greater than 0";
    public static final String INVALID_RESTAURANT_OWNER_MESSAGE = "The user is not the owner of the restaurant";
    public static final String CUSTOMER_HAS_ACTIVE_ORDER_EXCEPTION = "The customer already has an active order and cannot place a new one.";
    public static final String INVALID_DISH_QUANTITY_EXCEPTION = "The dish quantity must be a positive number greater than 0";
}
