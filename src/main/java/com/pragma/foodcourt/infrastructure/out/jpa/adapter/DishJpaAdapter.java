package com.pragma.foodcourt.infrastructure.out.jpa.adapter;

import com.pragma.foodcourt.domain.model.Dish;
import com.pragma.foodcourt.domain.spi.IDishPersistencePort;
import com.pragma.foodcourt.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.foodcourt.infrastructure.out.jpa.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;

    @Override
    public Dish save(Dish dish) {
        DishEntity mappedDishEntity = modelMapper.map(dish, DishEntity.class);
        DishEntity savedDishEntity = dishRepository.save(mappedDishEntity);

        return modelMapper.map(savedDishEntity, Dish.class);
    }
}
