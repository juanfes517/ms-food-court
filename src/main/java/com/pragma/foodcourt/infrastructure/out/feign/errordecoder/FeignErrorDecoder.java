package com.pragma.foodcourt.infrastructure.out.feign.errordecoder;

import com.pragma.foodcourt.infrastructure.exception.UserNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new UserNotFoundException(ExceptionConstants.USER_NOT_FOUND);
        }
        if (response.status() == HttpStatus.FORBIDDEN.value()) {
            return new BadCredentialsException(ExceptionConstants.BAD_CREDENTIALS);
        }

        return new RuntimeException(ExceptionConstants.GENERIC_ERROR);
    }
}
