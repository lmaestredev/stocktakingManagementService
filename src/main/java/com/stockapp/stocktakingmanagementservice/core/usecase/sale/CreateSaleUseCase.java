package com.stockapp.stocktakingmanagementservice.core.usecase.sale;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.SaleDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.SaleDtoRes;
import reactor.core.publisher.Mono;

public interface CreateSaleUseCase {

    Mono<SaleDtoRes> create(SaleDtoReq saleDtoReq);
}
