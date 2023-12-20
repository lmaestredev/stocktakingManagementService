package com.stockapp.stocktakingmanagementservice.core.usecase.product;

import com.stockapp.stocktakingmanagementservice.core.dtos.SaleProductDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface VerifyProductsByNameUseCase {
    Flux<SaleProductDto> validateProducts(List<SaleProductDto> products);
}
