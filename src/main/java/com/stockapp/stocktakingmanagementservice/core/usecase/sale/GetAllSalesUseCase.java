package com.stockapp.stocktakingmanagementservice.core.usecase.sale;

import com.stockapp.stocktakingmanagementservice.core.dtos.response.SaleDtoRes;
import reactor.core.publisher.Flux;

public interface GetAllSalesUseCase {
    Flux<SaleDtoRes> getAll();

}
