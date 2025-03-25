package com.pragma.foodcourt.domain.model;

public enum OrderStatusEnum {

    PENDING("PENDING"),
    PREPARING("PREPARING"),
    CANCELED("CANCELED"),
    READY("READY"),
    DELIVERED("DELIVERED");

    private final String statusName;

    OrderStatusEnum(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }
}
