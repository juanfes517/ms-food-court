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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
        Dish dish = this.verifyTheRestaurantOwner(dishId);
        
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

    @Override
    public Dish updateDishStatus(Long dishId, boolean status) {
        Dish dish = this.verifyTheRestaurantOwner(dishId);
        dish.setActive(status);

        return dishPersistencePort.save(dish);
    }

    @Override
    public Page<Dish> findAllDishes(Pageable pageable, String categoryName, Long restaurantId) {
        return dishPersistencePort.findAll(pageable, categoryName, restaurantId);
    }

    private Dish verifyTheRestaurantOwner(Long dishId) {
        Dish dish = dishPersistencePort.findById(dishId);
        String tokenEmail = jwtSecurityServicePort.getSubject();
        Long restaurantOwnerId = dish.getRestaurant().getOwnerId();

        boolean isUserTheRestaurantOwner = userExternalServicePort.userHasEmail(restaurantOwnerId, tokenEmail);

        if (!isUserTheRestaurantOwner) {
            throw new InvalidRestaurantOwnerException(ExceptionConstants.INVALID_RESTAURANT_OWNER_MESSAGE);
        }

        return dish;
    }
}
