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
    private final ErrorHandler errorHandler;

    @Autowired
    public CustomerHandler(CreateCustomerUseCase createCustomerUseCase, GetAllCustomersUseCase getAllCustomersUseCase, GetCustomerByIdUseCase getCustomerByIdUseCase, ErrorHandler errorHandler) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
        this.errorHandler = errorHandler;
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
        Mono<String> customerIdMono = Mono.just(request.pathVariable("customerId"));

        return customerIdMono.flatMap(customerId -> {
            return getCustomerByIdUseCase.getById(customerId)
                    .flatMap(customer -> {
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(customer);
                    })
                    .onErrorResume(error -> errorHandler.handleServiceError(error, customerId));
        });
    }

}
