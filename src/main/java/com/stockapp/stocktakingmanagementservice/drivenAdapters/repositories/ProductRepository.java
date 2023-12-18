package com.stockapp.stocktakingmanagementservice.drivenAdapters.repositories;

import com.stockapp.stocktakingmanagementservice.models.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
