package com.pragma.foodcourt.infrastructure.helper.constants;

public class SecurityConstants {

    private SecurityConstants() {}

    public static final String ADMIN_ROLE = "ADMIN";
    public static final String OWNER_ROLE = "OWNER";
    public static final String CUSTOMER_ROLE = "CUSTOMER";

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

    private static final String[] CUSTOMER_ENDPOINTS = {
            "/api/v1/restaurants",
            "/api/v1/dishes"
    };

    public static String[] getPublicEndpoints() {
        return PUBLIC_ENDPOINTS.clone();
    }

    public static String[] getAdminEndpoints() {
        return ADMIN_ENDPOINTS.clone();
    }

    public static String[] getOwnerEndpoints() {
        return OWNER_ENDPOINTS.clone();
    }

    public static String[] getCustomerEndpoints() {
        return CUSTOMER_ENDPOINTS.clone();
    }
}
