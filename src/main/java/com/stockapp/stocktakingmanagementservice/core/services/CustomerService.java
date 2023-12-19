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
        return customerRepository.save(customer).map(customerMapper::entityToDtoRes);
    }

    @Override
    public Flux<CustomerDtoRes> getAll() {
        return customerRepository.findAll().map(customerMapper::entityToDtoRes);
    }

    @Override
    public Mono<CustomerDtoRes> getById(String id) {
        return customerRepository.findById(id).map(customerMapper::entityToDtoRes);
    }
    
}
