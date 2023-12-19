package com.stockapp.stocktakingmanagementservice.core.usecase.product;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.ProductDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.ProductDtoRes;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CreateProductsByLotsUseCase {
    Mono<List<ProductDtoRes>> createLots(List<ProductDtoReq> productDtoReq);

}
