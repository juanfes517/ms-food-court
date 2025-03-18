package com.pragma.foodcourt.infrastructure.security.adapter;

import com.pragma.foodcourt.domain.spi.IJwtServiceUtils;
import org.springframework.stereotype.Component;

@Component
public class JwtUtilsAdapter implements IJwtServiceUtils {

    @Override
    public String extractSubjectFromToken() {
        //TODO: implements with JWT token
        return "test@mail.com";
    }
}
