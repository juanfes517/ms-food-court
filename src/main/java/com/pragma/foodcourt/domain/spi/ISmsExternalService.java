package com.pragma.foodcourt.domain.spi;

public interface ISmsExternalService {

    boolean notifyOrderReady(String cellPhoneNumber, String restaurantName, String securityPin);
}
