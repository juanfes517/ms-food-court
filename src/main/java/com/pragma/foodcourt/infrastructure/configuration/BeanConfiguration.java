package com.pragma.foodcourt.infrastructure.configuration;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.domain.api.ICategoryServicePort;
import com.pragma.foodcourt.domain.api.IDishServicePort;
import com.pragma.foodcourt.domain.api.IEmployeeAssignmentServicePort;
import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.*;
import com.pragma.foodcourt.domain.usecase.CategoryUseCase;
import com.pragma.foodcourt.domain.usecase.DishUseCase;
import com.pragma.foodcourt.domain.usecase.EmployeeAssignmentUseCase;
import com.pragma.foodcourt.domain.usecase.RestaurantUseCase;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public IRestaurantServicePort restaurantService(IRestaurantPersistencePort restaurantPersistencePort, IUserExternalServicePort userExternalServicePort) {
        return new RestaurantUseCase(restaurantPersistencePort, userExternalServicePort);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<RestaurantRequestDto, Restaurant>() {
            @Override
            protected void configure() {
                map().setOwnerId(source.getOwnerId());
                skip(destination.getId());
            }
        });

        return modelMapper;
    }

    @Bean
    public IDishServicePort dishService(
            IDishPersistencePort dishPersistencePort,
            IJwtSecurityServicePort jwtServiceUtils,
            IUserExternalServicePort userExternalServicePort) {
        return new DishUseCase(dishPersistencePort, jwtServiceUtils, userExternalServicePort);
    }

    @Bean
    public ICategoryServicePort categoryService(ICategoryPersistencePort categoryPersistencePort) {
        return new CategoryUseCase(categoryPersistencePort);
    }

    @Bean
    public IEmployeeAssignmentServicePort employeeAssignmentService(
            IRestaurantPersistencePort restaurantPersistencePort,
            IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort) {
        return new EmployeeAssignmentUseCase(restaurantPersistencePort, employeeAssignmentPersistencePort);
    }
}
