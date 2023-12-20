package com.stockapp.stocktakingmanagementservice.core.port.repositories;

import com.stockapp.stocktakingmanagementservice.core.models.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    Mono<Product> findByName(String name);

    Mono<Boolean> existsByName(String name);

    Flux<Product> findAllBy(Pageable pageable);

}
