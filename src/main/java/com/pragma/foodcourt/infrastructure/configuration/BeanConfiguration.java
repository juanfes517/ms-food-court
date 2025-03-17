package com.pragma.foodcourt.infrastructure.configuration;

import com.pragma.foodcourt.application.dto.request.RestaurantRequestDto;
import com.pragma.foodcourt.domain.api.IRestaurantServicePort;
import com.pragma.foodcourt.domain.model.Restaurant;
import com.pragma.foodcourt.domain.spi.IRestaurantPersistencePort;
import com.pragma.foodcourt.domain.spi.IUserExternalServicePort;
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
}
