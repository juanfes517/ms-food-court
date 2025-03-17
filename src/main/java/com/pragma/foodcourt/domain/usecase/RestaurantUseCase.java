package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.exception.InvalidCellPhoneNumberException;
import com.pragma.foodcourt.domain.exception.InvalidUserRoleException;
import com.pragma.foodcourt.domain.exception.NonNumericNitException;
import com.pragma.foodcourt.domain.exception.NumericNameException;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourt.domain.spi.IUserExternalServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserExternalServicePort userExternalServicePort;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        if (!userExternalServicePort.userHasRole(restaurant.getOwnerId(), "ROLE_OWNER")) {
            throw new InvalidUserRoleException(ExceptionConstants.ONLY_OWNER_EXCEPTION_MESSAGE);
        }
        if (!restaurant.isNumericCellPhoneNumber()) {
            throw new InvalidCellPhoneNumberException(ExceptionConstants.CELL_PHONE_NUMBER_IS_NOT_A_NUMBER_MESSAGE);
        }
        if (!restaurant.isCellPhoneNumberMax13Chars()) {
            throw new InvalidCellPhoneNumberException(ExceptionConstants.CELL_PHONE_NUMBER_LENGTH_EXCEPTION_MESSAGE);
        }
        if (!restaurant.isNumericNit()) {
            throw new NonNumericNitException(ExceptionConstants.NON_NUMERIC_NIT_EXCEPTION_MESSAGE);
        }
        if (!restaurant.isNameNotNumeric()) {
            throw new NumericNameException(ExceptionConstants.NUMERIC_NAME_EXCEPTION_MESSAGE);
        }

        return restaurantPersistencePort.save(restaurant);
    }

    @Override
    public Restaurant findRestaurantById(Long id) {
        return restaurantPersistencePort.findById(id);
    }
}
