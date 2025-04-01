package com.pragma.foodcourt.infrastructure.helper.constants;

public class FeignClientConstants {

    private FeignClientConstants() {}

    public static final String USER_HAS_ROLE_FEIGN_ENDPOINT = "/{user-id}/has-role";
    public static final String USER_HAS_EMAIL_FEIGN_ENDPOINT = "/{user-id}/has-email";
    public static final String GET_USER_ID_BY_EMAIL_FEIGN_ENDPOINT = "/id";
    public static final String GET_CELL_PHONE_NUMBER_FEIGN_ENDPOINT = "/{id}/cell-phone-number";
    public static final String GET_EMAIL_BY_USER_ID_FEIGN_ENDPOINT = "/email";

    public static final String SEND_ORDER_READY_MESSAGE_FEIGN_ENDPOINT = "/ready-message";

    public static final String GET_ORDER_TRACEABILITY_FEIGN_ENDPOINT = "/order-id";
    public static final String GET_RESTAURANT_EFFICIENCY_FEIGN_ENDPOINT = "/restaurant-efficiency";
    public static final String GET_EMPLOYEE_EFFICIENCY_FEIGN_ENDPOINT = "/employee-efficiency";
}
