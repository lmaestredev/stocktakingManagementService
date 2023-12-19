package com.stockapp.stocktakingmanagementservice.core.usecase.product;

import com.stockapp.stocktakingmanagementservice.core.dtos.response.ProductDtoRes;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;


public interface GetPaginatedProductsUseCase {
    Flux<ProductDtoRes> getPaginated(int pagina, int tamanoPagina);
}
