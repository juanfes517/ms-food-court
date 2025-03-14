package com.pragma.foodcourt.domain.spi;

public interface IUserExternalServicePort {

    boolean userHasRole(Long userId, String roleName);
}
