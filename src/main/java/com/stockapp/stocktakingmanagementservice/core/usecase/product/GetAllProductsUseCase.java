package com.stockapp.stocktakingmanagementservice.core.usecase.product;

import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.ProductDtoRes;
import reactor.core.publisher.Flux;

public interface GetAllProductsUseCase {
    Flux<ProductDtoRes> getAll();
}
