package com.stockapp.stocktakingmanagementservice.core.usecase.customer;

import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import reactor.core.publisher.Flux;

public interface GetAllCustomersUseCase {
    Flux<CustomerDtoRes> getAll();
}
