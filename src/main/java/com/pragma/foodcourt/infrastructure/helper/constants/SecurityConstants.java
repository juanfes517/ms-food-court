package com.pragma.foodcourt.infrastructure.helper.constants;

public class SecurityConstants {

    private SecurityConstants() {}

    public static final String ADMIN_ROLE = "ADMIN";
    public static final String OWNER_ROLE = "OWNER";
    public static final String CUSTOMER_ROLE = "CUSTOMER";
    public static final String EMPLOYEE_ROLE = "EMPLOYEE";

    private static final String[] PUBLIC_ENDPOINTS = {
            "/v1/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
    };

    private static final String[] ADMIN_ENDPOINTS = {
            "/api/v1/restaurants"
    };

    private static final String[] OWNER_ENDPOINTS = {
            "/api/v1/dishes/**",
            "/api/v1/employee-assignment"
    };

    private static final String[] CUSTOMER_GET_ENDPOINTS = {
            "/api/v1/restaurants",
            "/api/v1/dishes",
            "/api/v1/traceability/order-id"
    };

    private static final String[] CUSTOMER_POST_ENDPOINTS = {
            "/api/v1/orders"
    };

    private static final String[] CUSTOMER_PATCH_ENDPOINTS = {
            "/api/v1/orders/{order-id}/cancel-order"
    };

    private static final String[] EMPLOYEE_ENDPOINTS = {
            "/api/v1/orders/{order-id}/assign-employee",
            "/api/v1/orders/{order-id}/mark-ready",
            "/api/v1/orders/{order-id}/mark-delivered"
    };

    public static String[] getPublicEndpoints() {
        return PUBLIC_ENDPOINTS.clone();
    }

    public static String[] getAdminEndpoints() {
        return ADMIN_ENDPOINTS.clone();
    }

    public static String[] getEmployeeEndpoints() {
        return EMPLOYEE_ENDPOINTS.clone();
    }

    public static String[] getOwnerEndpoints() {
        return OWNER_ENDPOINTS.clone();
    }

    public static String[] getCustomerGetEndpoints() {
        return CUSTOMER_GET_ENDPOINTS.clone();
    }

    public static String[] getCustomerPatchEndpoints() {
        return CUSTOMER_PATCH_ENDPOINTS.clone();
    }

    public static String[] getCustomerPostEndpoints() {
        return CUSTOMER_POST_ENDPOINTS.clone();
    }
}
