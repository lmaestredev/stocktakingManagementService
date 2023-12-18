package com.stockapp.stocktakingmanagementservice.core.usecase;

import com.stockapp.stocktakingmanagementservice.core.dtos.CustomerDto;
import reactor.core.publisher.Mono;

public interface CreateCustomerUseCase {
    Mono<CustomerDto> createCustomer(CustomerDto customer);
}
