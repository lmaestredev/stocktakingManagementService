package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.CustomerDto;
import com.stockapp.stocktakingmanagementservice.core.usecase.CreateCustomerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    private final CreateCustomerUseCase createCustomerUseCase;

    @Autowired
    public CustomerHandler(CreateCustomerUseCase createCustomerUseCase) {
        this.createCustomerUseCase = createCustomerUseCase;
    }

    public Mono<ServerResponse> create(ServerRequest request){
        Mono<CustomerDto> customerDtoMono = request.bodyToMono(CustomerDto.class);
        return customerDtoMono.flatMap(customerDto -> {
           return createCustomerUseCase.createCustomer(customerDto)
                   .flatMap(created ->{
                       return ServerResponse.ok()
                               .contentType(MediaType.APPLICATION_JSON)
                               .bodyValue(created);
                   });
        });
    }
}
