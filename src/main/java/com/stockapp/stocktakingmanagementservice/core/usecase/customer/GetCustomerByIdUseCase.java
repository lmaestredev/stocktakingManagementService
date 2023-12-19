package com.stockapp.stocktakingmanagementservice.core.usecase.customer;

import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import reactor.core.publisher.Mono;

public interface GetCustomerByIdUseCase {
    Mono<CustomerDtoRes> getById(String id);
}
