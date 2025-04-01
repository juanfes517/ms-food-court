package com.pragma.foodcourt.infrastructure.out.feign.client;

import com.pragma.foodcourt.infrastructure.configuration.FeignClientConfiguration;
import com.pragma.foodcourt.infrastructure.helper.constants.FeignClientConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${USER_MICROSERVICE_NAME}", url = "${USER_MICROSERVICE_HOST}", configuration = FeignClientConfiguration.class)
public interface UserFeignClient {

    @GetMapping(FeignClientConstants.USER_HAS_ROLE_FEIGN_ENDPOINT)
    Boolean userHasRole(@PathVariable("user-id") Long userId, @RequestParam() String roleName);

    @GetMapping(FeignClientConstants.USER_HAS_EMAIL_FEIGN_ENDPOINT)
    Boolean userHasEmail(@PathVariable("user-id") Long userId, @RequestParam() String email);

    @GetMapping(FeignClientConstants.GET_USER_ID_BY_EMAIL_FEIGN_ENDPOINT)
    Long getUserIdByEmail(@RequestParam String email);

    @GetMapping(FeignClientConstants.GET_CELL_PHONE_NUMBER_FEIGN_ENDPOINT)
    String getCellPhoneNumberById(@PathVariable("id") Long userId);

    @GetMapping(FeignClientConstants.GET_EMAIL_BY_USER_ID_FEIGN_ENDPOINT)
    String getEmailByUserId(@RequestParam Long userId);
}
