package com.pragma.foodcourt.domain.spi;

public interface IJwtSecurityServicePort {

    boolean validateToken(String token);

    String getClaim(String token, String claim);

    String getSubject(String token);

    String getSubject();
}
