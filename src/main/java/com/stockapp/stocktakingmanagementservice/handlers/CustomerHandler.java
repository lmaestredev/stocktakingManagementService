package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.CustomerDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.CreateCustomerUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.GetAllCustomersUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.GetCustomerByIdUseCase;
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
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;

    @Autowired
    public CustomerHandler(CreateCustomerUseCase createCustomerUseCase, GetAllCustomersUseCase getAllCustomersUseCase, GetCustomerByIdUseCase getCustomerByIdUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CustomerDtoReq> customerDtoMono = request.bodyToMono(CustomerDtoReq.class);
        return customerDtoMono.flatMap(customerDto -> {
            return createCustomerUseCase.create(customerDto).flatMap(created -> {
                return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(created);
            });
        });
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        Flux<CustomerDtoRes> customers = getAllCustomersUseCase.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(customers, CustomerDtoRes.class);

    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String customerId = request.pathVariable("customerId");

        return getCustomerByIdUseCase.getById(customerId).flatMap(created -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(created)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });

    }

}
