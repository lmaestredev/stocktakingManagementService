package com.stockapp.stocktakingmanagementservice.core.port;

import com.stockapp.stocktakingmanagementservice.core.models.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
}
