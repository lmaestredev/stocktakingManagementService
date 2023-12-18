package com.stockapp.stocktakingmanagementservice.config;

import com.stockapp.stocktakingmanagementservice.utils.mappers.CustomerMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public CustomerMapper customerMapper(){
        return Mappers.getMapper(CustomerMapper.class);
    }
}
