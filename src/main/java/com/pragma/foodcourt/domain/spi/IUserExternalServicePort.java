package com.pragma.foodcourt.domain.spi;

public interface IUserExternalServicePort {

    boolean userHasRole(Long userId, String roleName);

    boolean userHasEmail(Long userId, String email);

    Long getUserIdByEmail(String email);

    String getCellPhoneNumberById(Long userId);
}
