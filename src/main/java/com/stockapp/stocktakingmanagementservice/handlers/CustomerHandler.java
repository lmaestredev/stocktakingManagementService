package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.CustomerDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import com.stockapp.stocktakingmanagementservice.core.models.Customer;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.CreateCustomerUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.GetAllCustomersUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetAllCustomersUseCase getAllCustomersUseCase;

    @Autowired
    public CustomerHandler(CreateCustomerUseCase createCustomerUseCase, GetAllCustomersUseCase getAllCustomersUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CustomerDtoReq> customerDtoMono = request.bodyToMono(CustomerDtoReq.class);
        return customerDtoMono.flatMap(customerDto -> {
            return createCustomerUseCase.createCustomer(customerDto)
                    .flatMap(created -> {
                        return ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(created);
                    });
        });
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        Flux<CustomerDtoRes> customers = getAllCustomersUseCase.getAll();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(customers, CustomerDtoRes.class);

    }
}
