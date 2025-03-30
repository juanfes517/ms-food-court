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
    public static final String INVALID_DISH_RESTAURANT_EXCEPTION = "The dish does not belong to the restaurant";
    public static final String ORDER_NOT_FROM_EMPLOYEE_RESTAURANT_EXCEPTION = "The order does not belong to the employee's restaurant";
    public static final String INVALID_ORDER_STATUS_EXCEPTION = "It is not possible to change the order status";
    public static final String ORDER_NOT_ASSIGNED_TO_EMPLOYEE_EXCEPTION = "The order was not assigned to the employee";

    public static final String PENDING_STATUS_EXCEPTION = "It is only possible to assign an order when it is in PENDING status";
    public static final String PREPARING_STATUS_EXCEPTION = "It is only possible to mark an order as READY when it is in PREPARING status";
    public static final String READY_STATUS_EXCEPTION = "An order can only be delivered when it is in READY status";
    public static final String CANCEL_STATUS_EXCEPTION = "Sorry, your order is already being prepared and cannot be cancelled";

    public static final String ORDER_NOT_CREATED_BY_THE_CUSTOMER = "The order was not created by the customer";
    public static final String NOTIFICATION_FAILED_EXCEPTION = "The notification delivery failed. Please check the cell phone number";
    public static final String INVALID_SECURITY_PIN_EXCEPTION = "The security PIN does not match.";
}
