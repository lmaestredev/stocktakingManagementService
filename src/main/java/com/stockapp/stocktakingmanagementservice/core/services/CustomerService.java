package com.stockapp.stocktakingmanagementservice.core.services;

import com.stockapp.stocktakingmanagementservice.core.dtos.CustomerDto;
import com.stockapp.stocktakingmanagementservice.core.models.Customer;
import com.stockapp.stocktakingmanagementservice.core.port.CustomerRepository;
import com.stockapp.stocktakingmanagementservice.core.usecase.CreateCustomerUseCase;
import com.stockapp.stocktakingmanagementservice.utils.mappers.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerService implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Mono<CustomerDto> createCustomer(CustomerDto customerDto) {
        Customer customer = customerMapper.dtoToEntity(customerDto);
        return customerRepository.save(customer).map(customerMapper::entityToDto);
    }
}
