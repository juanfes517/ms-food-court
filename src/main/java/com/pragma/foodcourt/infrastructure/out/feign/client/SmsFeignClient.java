package com.pragma.foodcourt.infrastructure.out.feign.client;

import com.pragma.foodcourt.infrastructure.configuration.FeignClientConfiguration;
import com.pragma.foodcourt.infrastructure.helper.constants.FeignClientConstants;
import com.pragma.foodcourt.infrastructure.out.feign.dto.MessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${MESSAGE_MICROSERVICE_NAME}", url = "${MESSAGE_MICROSERVICE_HOST}", configuration = FeignClientConfiguration.class)

public interface SmsFeignClient {

    @PostMapping(FeignClientConstants.SEND_ORDER_READY_MESSAGE_FEIGN_ENDPOINT)
    String sendOrderReadyMessage(@RequestBody MessageDto message);
}
