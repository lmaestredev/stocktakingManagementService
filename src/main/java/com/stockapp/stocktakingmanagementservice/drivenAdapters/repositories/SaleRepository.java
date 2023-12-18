package com.stockapp.stocktakingmanagementservice.drivenAdapters.repositories;

import com.stockapp.stocktakingmanagementservice.models.Sale;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SaleRepository extends ReactiveMongoRepository<Sale, String> {
}
