package com.pragma.foodcourt.domain.spi;

import org.springframework.web.bind.annotation.RequestParam;

public interface IUserExternalServicePort {

    boolean userHasRole(Long userId, String roleName);

    boolean userHasEmail(Long userId, String email);

    Long getUserIdByEmail(String email);

    String getCellPhoneNumberById(Long userId);

    String getEmailByUserId(@RequestParam Long userId);
}
