package com.pragma.foodcourt.infrastructure.helper.jwt;

public class TokenHolder {

    private TokenHolder() {
    }

    private static final ThreadLocal<String> accessToken = new ThreadLocal<>();

    public static void setToken(String token) {
        accessToken.set(token);
    }

    public static String getToken() {
        return accessToken.get();
    }

    public static void clear() {
        accessToken.remove();
    }
}
