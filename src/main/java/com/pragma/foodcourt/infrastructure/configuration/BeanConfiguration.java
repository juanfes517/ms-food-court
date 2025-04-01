package com.pragma.foodcourt.infrastructure.configuration;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.domain.api.*;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.*;
import com.pragma.foodcourt.domain.usecase.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
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

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

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

    @Bean
    public IOrderDishServicePort orderDishService(
            IOrderDishPersistencePort orderDishPersistencePort,
            IDishPersistencePort dishPersistencePort) {
        return new OrderDishUseCase(orderDishPersistencePort, dishPersistencePort);
    }

    @Bean
    public IOrderServicePort orderService(
            IUserExternalServicePort userExternalServicePort,
            IOrderPersistencePort orderPersistencePort,
            IJwtSecurityServicePort jwtSecurityServicePort,
            IEmployeeAssignmentPersistencePort employeeAssignmentPersistencePort,
            ISmsExternalService smsExternalService,
            ITraceabilityExternalService traceabilityExternalService) {
        return new OrderUseCase(userExternalServicePort, orderPersistencePort, jwtSecurityServicePort,
                employeeAssignmentPersistencePort, smsExternalService, traceabilityExternalService);
    }

    @Bean
    public ITraceabilityServicePort traceabilityService(
            ITraceabilityExternalService traceabilityExternalService,
            IUserExternalServicePort userExternalService,
            IJwtSecurityServicePort jwtSecurityService,
            IRestaurantPersistencePort restaurantPersistencePort,
            IOrderPersistencePort orderPersistencePort) {
        return new TraceabilityUseCase(traceabilityExternalService, userExternalService, jwtSecurityService,
                restaurantPersistencePort, orderPersistencePort);
    }
}
