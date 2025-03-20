package com.pragma.foodcourt.infrastructure.out.feign.interceptor;

import com.pragma.foodcourt.infrastructure.helper.jwt.TokenHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = TokenHolder.getToken();
        requestTemplate.header("Authorization", "Bearer " + token);
    }
}
