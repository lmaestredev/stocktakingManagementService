package com.stockapp.stocktakingmanagementservice.config;

import com.stockapp.stocktakingmanagementservice.utils.mappers.CustomerMapper;
import com.stockapp.stocktakingmanagementservice.utils.mappers.ProductMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
public class MapperConfig {

    @Bean
    public CustomerMapper customerMapper() {
        return Mappers.getMapper(CustomerMapper.class);
    }

    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }
}
