package com.stockapp.stocktakingmanagementservice.core.usecase.customer;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.CustomerDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import reactor.core.publisher.Mono;

public interface CreateCustomerUseCase {
    Mono<CustomerDtoRes> createCustomer(CustomerDtoReq customer);
}
