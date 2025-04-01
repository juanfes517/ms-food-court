package com.pragma.foodcourt.infrastructure.helper.constants;

public class ApiConstants {

    private ApiConstants() {}

    public static final String DISH_CONTROLLER = "/api/v1/dishes";
    public static final String UPDATE_DISH_STATUS_ENDPOINT = "/{dish-id}/status";

    public static final String EMPLOYEE_ASSIGNMENT_CONTROLLER = "/api/v1/employee-assignment";

    public static final String ORDER_CONTROLLER = "/api/v1/orders";
    public static final String ASSIGN_ORDER_ENDPOINT = "/{order-id}/assign-employee";
    public static final String MARK_ORDER_READY_ENDPOINT = "/{order-id}/mark-ready";
    public static final String MARK_ORDER_DELIVERED_ENDPOINT = "/{order-id}/mark-delivered";
    public static final String CANCEL_ORDER_ENDPOINT = "/{order-id}/cancel-order";

    public static final String RESTAURANT_CONTROLLER = "/api/v1/restaurants";

    public static final String TRACEABILITY_CONTROLLER = "/api/v1/traceability";
    public static final String GET_ORDER_TRACEABILITY_ENDPOINT = "/order-id";
    public static final String GET_RESTAURANT_EFFICIENCY_ENDPOINT = "/restaurant-efficiency";
    public static final String GET_EMPLOYEE_EFFICIENCY_ENDPOINT = "/employee-efficiency";

    public static final String SAVE_RESTAURANT_DESCRIPTION = "Save a new restaurant";
    public static final String SAVE_DISH_DESCRIPTION = "Save a new dish";
    public static final String SAVE_ORDER_DESCRIPTION = "Save a new order";
    public static final String GET_EMPLOYEE_EFFICIENCY_DESCRIPTION = "Get the efficiency of all employees of the restaurant";
    public static final String GET_RESTAURANT_EFFICIENCY_DESCRIPTION = "Get the efficiency of all order of the restaurant";
    public static final String GET_TRACEABILITY_DESCRIPTION = "Get all traceability by order id";
    public static final String GET_ALL_ORDER_DESCRIPTION = "Get all orders by status and restaurant id";
    public static final String ASSIGN_ORDER_DESCRIPTION = "Assign an order to an employee";
    public static final String MARK_ORDER_AS_READY_DESCRIPTION = "Mark an order as ready";
    public static final String MARK_ORDER_AS_DELIVERED_DESCRIPTION = "Mark an order as delivered";
    public static final String CANCEL_ORDER_DESCRIPTION = "Cancel an order";
    public static final String ASSIGN_EMPLOYEE_TO_RESTAURANT_DESCRIPTION = "Assign a new employee to a restaurant";
    public static final String GET_ALL_RESTAURANTS_DESCRIPTION = "Return all restaurants";
    public static final String GET_ALL_DISHES_DESCRIPTION = "Return all dishes from a restaurant";
    public static final String UPDATE_DISH_DESCRIPTION = "Update a existing dish";
    public static final String UPDATE_DISH_STATUS_DESCRIPTION = "Enable or disable dish";
    public static final String OK_DESCRIPTION = "Request successful";
    public static final String OBJECT_CREATED_DESCRIPTION = "Object created";
    public static final String FORBIDDEN_DESCRIPTION = "Permission denied";
    public static final String CONFLICT_DESCRIPTION = "Conflict occurred";
    public static final String BAD_REQUEST_DESCRIPTION = "Bad request";
    public static final String NOT_FOUND_DESCRIPTION = "Not found";
    public static final String INTERNAL_SERVER_ERROR_DESCRIPTION = "Internal server error";
}
