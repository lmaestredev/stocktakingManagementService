package com.stockapp.stocktakingmanagementservice.core.services;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.CustomerDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.CustomerDtoRes;
import com.stockapp.stocktakingmanagementservice.core.models.Customer;
import com.stockapp.stocktakingmanagementservice.core.port.CustomerRepository;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.CreateCustomerUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.GetAllCustomersUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.customer.GetCustomerByIdUseCase;
import com.stockapp.stocktakingmanagementservice.utils.mappers.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService implements CreateCustomerUseCase, GetAllCustomersUseCase, GetCustomerByIdUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Mono<CustomerDtoRes> create(CustomerDtoReq customerDtoReq) {
        Customer customer = customerMapper.dtoToEntity(customerDtoReq);
        return customerRepository.existsByName(customer.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("El customer ya existe en la base de datos."));
                    } else {
                        if (customer.getName() == null) {
                            return Mono.error(new RuntimeException("Nombre no puede estar vacio o ser nulo"));
                        } else if (customer.getName().isEmpty()) {
                            return Mono.error(new RuntimeException("Nombre no puede estar vacio o ser nulo"));
                        } else {
                            return customerRepository.save(customer)
                                    .map(customerMapper::entityToDtoRes);
                        }
                    }
                });
    }

    @Override
    public Flux<CustomerDtoRes> getAll() {
        return customerRepository.findAll()
                .collectList()
                .flatMapMany(customers -> {
                    if (customers.isEmpty()) {
                        return Mono.error(new RuntimeException("La lista está vacia"));
                    } else {
                        return Flux.fromIterable(customers).map(customerMapper::entityToDtoRes);
                    }
                });
    }

    @Override
    public Mono<CustomerDtoRes> getById(String id) {
        return customerRepository.findById(id)
                .flatMap(customer -> Mono.just(customerMapper.entityToDtoRes(customer)))
                .switchIfEmpty(Mono.error(new RuntimeException("El cliente no existe")));
    }

}
