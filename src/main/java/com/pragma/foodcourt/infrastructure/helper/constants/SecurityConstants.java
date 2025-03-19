package com.pragma.foodcourt.infrastructure.helper.constants;

public class SecurityConstants {

    private SecurityConstants() {}


    public static final String[] PUBLIC_ENDPOINTS = {
            "/v1/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
    };

    public static final String[] ADMIN_ENDPOINTS = {
            "/api/v1/restaurants"
    };

    public static final String ADMIN_ROLE = "ADMIN";
}
