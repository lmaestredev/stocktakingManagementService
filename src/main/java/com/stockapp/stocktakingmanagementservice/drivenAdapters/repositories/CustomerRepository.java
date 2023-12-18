package com.stockapp.stocktakingmanagementservice.drivenAdapters.repositories;

import com.stockapp.stocktakingmanagementservice.models.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
