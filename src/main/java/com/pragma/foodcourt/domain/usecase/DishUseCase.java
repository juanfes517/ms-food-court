package com.pragma.foodcourt.domain.usecase;

import com.pragma.foodcourt.domain.api.IDishServicePort;
import com.pragma.foodcourt.domain.exception.InvalidPriceException;
import com.pragma.foodcourt.domain.exception.InvalidRestaurantOwnerException;
import com.pragma.foodcourt.domain.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.spi.IDishPersistencePort;
import com.pragma.foodcourt.domain.spi.IJwtSecurityServicePort;
import com.pragma.foodcourt.domain.spi.IUserExternalServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;
    private final IJwtSecurityServicePort jwtSecurityServicePort;
    private final IUserExternalServicePort userExternalServicePort;

    @Override
    public Dish saveDish(Dish dish) {
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long restaurantOwnerId = dish.getRestaurant().getOwnerId();
        
        boolean isUserTheRestaurantOwner = userExternalServicePort.userHasEmail(restaurantOwnerId, tokenEmail);

        if (!isUserTheRestaurantOwner) {
            throw new InvalidRestaurantOwnerException(ExceptionConstants.INVALID_RESTAURANT_OWNER_MESSAGE);
        }

        if (!dish.isValidPrice()) {
            throw new InvalidPriceException(ExceptionConstants.INVALID_PRICE_EXCEPTION_MESSAGE);
        }

        return dishPersistencePort.save(dish);
    }

    @Override
    public Dish updateDish(Long dishId, Integer price, String description) {
        Dish dish = dishPersistencePort.findById(dishId);
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long restaurantOwnerId = dish.getRestaurant().getOwnerId();

        boolean isUserTheRestaurantOwner = userExternalServicePort.userHasEmail(restaurantOwnerId, tokenEmail);

        if (!isUserTheRestaurantOwner) {
            throw new InvalidRestaurantOwnerException(ExceptionConstants.INVALID_RESTAURANT_OWNER_MESSAGE);
        }
        
        if (price != null) {
            dish.setPrice(price);

            if (!dish.isValidPrice()) {
                throw new InvalidPriceException(ExceptionConstants.INVALID_PRICE_EXCEPTION_MESSAGE);
            }
        }

        if (description != null) {
            dish.setDescription(description);
        }

        return dishPersistencePort.save(dish);
    }
}
