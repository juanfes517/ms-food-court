package com.pragma.foodcourt.infrastructure.out.feign.adapter;

import com.pragma.foodcourt.domain.spi.ISmsExternalService;
import com.pragma.foodcourt.infrastructure.out.feign.client.SmsFeignClient;
import com.pragma.foodcourt.infrastructure.out.feign.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmsFeignAdapter implements ISmsExternalService {

    private final SmsFeignClient smsFeignClient;

    @Override
    public boolean notifyOrderReady(String cellPhoneNumber, String restaurantName, String securityPin) {
        MessageDto message = MessageDto.builder()
                .customerCellPhoneNumber(cellPhoneNumber)
                .restaurantName(restaurantName)
                .securityPin(securityPin)
                .build();

        String result = smsFeignClient.sendOrderReadyMessage(message);
        return result != null;
    }
}
