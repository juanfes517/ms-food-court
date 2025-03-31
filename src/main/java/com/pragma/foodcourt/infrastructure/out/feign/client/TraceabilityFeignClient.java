package com.pragma.foodcourt.infrastructure.out.feign.client;

import com.pragma.foodcourt.domain.model.CreateTraceability;
import com.pragma.foodcourt.infrastructure.configuration.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${TRACEABILITY_MICROSERVICE_NAME}", url = "${TRACEABILITY_MICROSERVICE_HOST}", configuration = FeignClientConfiguration.class)
public interface TraceabilityFeignClient {

    @PostMapping
    void createTraceability(@RequestBody CreateTraceability createTraceability);
}
