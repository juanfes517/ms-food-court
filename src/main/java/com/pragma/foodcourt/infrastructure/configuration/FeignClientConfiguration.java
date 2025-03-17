package com.pragma.foodcourt.infrastructure.configuration;

import com.pragma.foodcourt.infrastructure.out.feign.errordecoder.FeignErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}
