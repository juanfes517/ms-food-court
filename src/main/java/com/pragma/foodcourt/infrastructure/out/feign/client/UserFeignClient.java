package com.pragma.foodcourt.infrastructure.out.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${USER_MICROSERVICE_NAME}", url = "${USER_MICROSERVICE_HOST}")
public interface UserFeignClient {

    @GetMapping("/{user-id}/has-role")
    Boolean userHasRole(@PathVariable("user-id") Long userId, @RequestParam() String roleName);
}
