package com.stockapp.stocktakingmanagementservice.config;

import com.stockapp.stocktakingmanagementservice.handlers.CustomerHandler;
import com.stockapp.stocktakingmanagementservice.handlers.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {

    @Bean
    public RouterFunction<ServerResponse> customerRoutes(CustomerHandler customerHandler) {
        return RouterFunctions
                .route(POST("/customers/create"), customerHandler::create)
                .andRoute(GET("/customers/getAll"), customerHandler::getAll)
                .andRoute(GET("/customers/getById/{customerId}"), customerHandler::getById);

    }

    @Bean
    public RouterFunction<ServerResponse> productRoutes(ProductHandler productHandler) {
        return RouterFunctions
                .route(POST("/products/create"), productHandler::create)
                .andRoute(GET("/products/getAll"), productHandler::getAll)
                .andRoute(GET("/products/getById/{productId}"), productHandler::getById);

    }

}
