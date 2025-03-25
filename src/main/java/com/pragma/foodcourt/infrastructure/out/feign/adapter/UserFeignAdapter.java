package com.pragma.foodcourt.infrastructure.out.feign.adapter;

import com.pragma.foodcourt.domain.spi.IUserExternalServicePort;
import com.pragma.foodcourt.infrastructure.out.feign.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFeignAdapter implements IUserExternalServicePort {

    private final UserFeignClient userFeignClient;

    @Override
    public boolean userHasRole(Long userId, String roleName) {
        return userFeignClient.userHasRole(userId, roleName);
    }

    @Override
    public boolean userHasEmail(Long userId, String email) {
        return userFeignClient.userHasEmail(userId, email);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        return userFeignClient.getUserIdByEmail(email);
    }
}
