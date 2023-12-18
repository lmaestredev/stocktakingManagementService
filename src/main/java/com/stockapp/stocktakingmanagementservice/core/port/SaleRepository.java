package com.stockapp.stocktakingmanagementservice.core.port;

import com.stockapp.stocktakingmanagementservice.core.models.Sale;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SaleRepository extends ReactiveMongoRepository<Sale, String> {
}
