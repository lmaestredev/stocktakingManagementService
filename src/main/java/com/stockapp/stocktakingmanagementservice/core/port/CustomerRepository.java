package com.stockapp.stocktakingmanagementservice.core.port;

import com.stockapp.stocktakingmanagementservice.core.models.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Boolean> existsByName(String name);

}
