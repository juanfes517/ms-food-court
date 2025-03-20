package com.pragma.foodcourt.infrastructure.configuration;

import com.pragma.foodcourt.infrastructure.out.feign.errordecoder.FeignErrorDecoder;
import com.pragma.foodcourt.infrastructure.out.feign.interceptor.FeignClientInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public FeignClientInterceptor feignClientInterceptor() {
        return new FeignClientInterceptor();
    }
}
