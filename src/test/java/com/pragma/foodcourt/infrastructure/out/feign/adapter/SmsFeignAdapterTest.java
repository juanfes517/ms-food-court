package com.pragma.foodcourt.infrastructure.out.feign.adapter;

import com.pragma.foodcourt.infrastructure.out.feign.client.SmsFeignClient;
import com.pragma.foodcourt.infrastructure.out.feign.dto.MessageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsFeignAdapterTest {

    @InjectMocks
    private SmsFeignAdapter smsFeignAdapter;

    @Mock
    private SmsFeignClient smsFeignClient;

    @Test
    void notifyOrderReady_WhenReturnTrue() {
        String cellPhoneNumber = "123456789";
        String restaurantName = "Restaurant Name";
        String securityPin = "123456";
        String smsResult = "success";

        when(smsFeignClient.sendOrderReadyMessage(any(MessageDto.class)))
                .thenReturn(smsResult);

        boolean result = smsFeignAdapter.notifyOrderReady(cellPhoneNumber, restaurantName, securityPin);

        assertTrue(result);
    }

    @Test
    void notifyOrderReady_WhenReturnFalse() {
        String cellPhoneNumber = "123456789";
        String restaurantName = "Restaurant Name";
        String securityPin = "123456";

        when(smsFeignClient.sendOrderReadyMessage(any(MessageDto.class)))
                .thenReturn(null);

        boolean result = smsFeignAdapter.notifyOrderReady(cellPhoneNumber, restaurantName, securityPin);

        assertFalse(result);
    }
}