package com.stockapp.stocktakingmanagementservice.core.port;

import com.stockapp.stocktakingmanagementservice.core.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
