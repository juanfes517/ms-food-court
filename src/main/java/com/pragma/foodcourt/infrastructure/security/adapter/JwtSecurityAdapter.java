package com.pragma.foodcourt.infrastructure.security.adapter;

import com.pragma.foodcourt.domain.spi.IJwtSecurityServicePort;
import org.springframework.stereotype.Component;

@Component
public class JwtSecurityAdapter implements IJwtSecurityServicePort {

    @Override
    public String extractSubjectFromToken() {
        //TODO: implements with JWT token
        return "test@mail.com";
    }
}
