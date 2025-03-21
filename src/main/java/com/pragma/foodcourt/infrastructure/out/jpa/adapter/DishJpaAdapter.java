package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.spi.IDishPersistencePort;
import com.pragma.foodcourt.infrastructure.exception.DishNotFoundException;
import com.pragma.foodcourt.infrastructure.helper.constants.ExceptionConstants;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.DishRepository;
import com.pragma.foodcourt.infrastructure.out.jpa.specification.IDishSpecification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;
    private final IDishSpecification dishSpecification;

    @Override
    public Dish save(Dish dish) {
        DishEntity mappedDishEntity = modelMapper.map(dish, DishEntity.class);
        DishEntity savedDishEntity = dishRepository.save(mappedDishEntity);

        return modelMapper.map(savedDishEntity, Dish.class);
    }

    @Override
    public Dish findById(Long id) {
        DishEntity dishEntity = dishRepository.findById(id)
                .orElseThrow(() -> new DishNotFoundException(ExceptionConstants.DISH_NOT_FOUND));

        return modelMapper.map(dishEntity, Dish.class);
    }

    @Override
    public Page<Dish> findAll(Pageable pageable, String categoryName, Long restaurantId) {
        Specification<DishEntity> especification = Specification.where(dishSpecification.hasRestaurant(restaurantId))
                .and(dishSpecification.hasCategory(categoryName));
        Page<DishEntity> dishEntities = dishRepository.findAll(especification, pageable);

        return dishEntities.map(dishEntity -> modelMapper.map(dishEntity, Dish.class));
    }
}
