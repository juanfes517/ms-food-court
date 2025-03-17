package com.pragma.foodcourt.domain.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private String cellPhoneNumber;
    private String logoUrl;
    private String nit;
    private Long ownerId;

    public boolean isNumericCellPhoneNumber() {
        String validationCellPhoneNumber = this.cellPhoneNumber;

        if (validationCellPhoneNumber.charAt(0) == '+') {
            validationCellPhoneNumber = validationCellPhoneNumber.substring(1);
        }

        return validationCellPhoneNumber.chars().allMatch(Character::isDigit);
    }

    public boolean isNumericNit() {
        return this.nit.chars().allMatch(Character::isDigit);
    }

    public boolean isCellPhoneNumberMax13Chars () {
        return this.cellPhoneNumber.length() <= 13;
    }

    public boolean isNameNotNumeric() {
        return !this.name.matches("\\d+");
    }
}
