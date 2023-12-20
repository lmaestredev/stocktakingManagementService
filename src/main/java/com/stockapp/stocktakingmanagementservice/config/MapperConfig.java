package com.stockapp.stocktakingmanagementservice.config;

import com.stockapp.stocktakingmanagementservice.utils.mappers.CustomerMapper;
import com.stockapp.stocktakingmanagementservice.utils.mappers.ProductMapper;
import com.stockapp.stocktakingmanagementservice.utils.mappers.SaleMapper;
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

    @Bean
    public SaleMapper saleMapper() {
        return Mappers.getMapper(SaleMapper.class);
    }
}
