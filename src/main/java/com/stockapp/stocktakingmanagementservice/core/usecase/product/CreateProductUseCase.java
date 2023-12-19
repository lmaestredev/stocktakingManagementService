package com.stockapp.stocktakingmanagementservice.core.usecase.product;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.CustomerDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.request.ProductDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.ProductDtoRes;
import reactor.core.publisher.Mono;

public interface CreateProductUseCase {
    Mono<ProductDtoRes> create(ProductDtoReq productDtoReq);
}
