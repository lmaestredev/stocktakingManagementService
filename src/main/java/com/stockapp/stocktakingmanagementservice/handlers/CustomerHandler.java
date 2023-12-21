package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.RabbitPublisherDto;
import com.stockapp.stocktakingmanagementservice.core.dtos.request.CustomerDtoReq;
import com.stockapp.stocktakingmanagementservice.core.port.bus.RabbitMqPublisher;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.CreateCustomerUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.GetAllCustomersUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.GetCustomerByIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetAllCustomersUseCase getAllCustomersUseCase;
    private final GetCustomerByIdUseCase getCustomerByIdUseCase;
    private final ErrorHandler errorHandler;
    private final RabbitMqPublisher rabbitMqPublisher;

    @Autowired
    public CustomerHandler(CreateCustomerUseCase createCustomerUseCase, GetAllCustomersUseCase getAllCustomersUseCase, GetCustomerByIdUseCase getCustomerByIdUseCase, ErrorHandler errorHandler, RabbitMqPublisher rabbitMqPublisher) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getAllCustomersUseCase = getAllCustomersUseCase;
        this.getCustomerByIdUseCase = getCustomerByIdUseCase;
        this.errorHandler = errorHandler;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CustomerDtoReq> customerDtoReqMono = request.bodyToMono(CustomerDtoReq.class);
        return request.bodyToMono(CustomerDtoReq.class).flatMap(customerDto -> {
            return createCustomerUseCase.create(customerDto)
                    .flatMap(created -> {
                        rabbitMqPublisher.publishCustomer(new RabbitPublisherDto("createCustomerUseCase", created));
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(created);
                    })
                    .onErrorResume(error -> {
                        Object customerData = customerDto.toString();
                        rabbitMqPublisher.publishErrorMessage(new RabbitPublisherDto("createCustomerUseCase", customerData, error.getMessage()));
                        return errorHandler.handleServiceError(error, customerDto);
                    });
        });
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        return getAllCustomersUseCase.getAll()
                .collectList()
                .flatMap(customers -> {
                    rabbitMqPublisher.publishCustomer(new RabbitPublisherDto("getAllCustomersUseCase", customers));
                    return ServerResponse.ok().bodyValue(customers);
                })
                .onErrorResume(error -> {
                    rabbitMqPublisher.publishErrorMessage(new RabbitPublisherDto("getAllCustomersUseCase", "", error.getMessage()));
                    return errorHandler.handleServiceError(error, "");
                });
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Mono<String> customerIdMono = Mono.just(request.pathVariable("customerId"));

        return customerIdMono.flatMap(customerId -> {
            return getCustomerByIdUseCase.getById(customerId)
                    .flatMap(customer -> {
                        rabbitMqPublisher.publishCustomer(new RabbitPublisherDto("getCustomerByIdUseCase", customer));
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(customer);
                    })
                    .onErrorResume(error -> {
                        rabbitMqPublisher.publishErrorMessage(new RabbitPublisherDto("getCustomerByIdUseCase", customerId, error.getMessage()));
                        return errorHandler.handleServiceError(error, customerId);
                    });
        });
    }

}
