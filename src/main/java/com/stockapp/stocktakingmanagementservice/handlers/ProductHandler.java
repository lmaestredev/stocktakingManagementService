package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.ProductDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.ProductDtoRes;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.CreateProductUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.GetAllProductsUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.GetProductByIdUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {

    private final CreateProductUseCase createProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final ErrorHandler errorHandler;

    @Autowired
    public ProductHandler(CreateProductUseCase createProductUseCase, GetAllProductsUseCase getAllProductsUseCase, GetProductByIdUseCase getProductByIdUseCase, ErrorHandler errorHandler) {
        this.createProductUseCase = createProductUseCase;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.errorHandler = errorHandler;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ProductDtoReq.class).flatMap(productDto -> {
            return createProductUseCase.create(productDto)
                    .flatMap(created -> {
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(created);
                    })
                    .onErrorResume(error -> errorHandler.handleServiceError(error, productDto));
        });
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return getAllProductsUseCase.getAll()
                .collectList()
                .flatMap(products -> {
                    return ServerResponse.ok().bodyValue(products);
                })
                .onErrorResume(error -> errorHandler.handleServiceError(error, ""));
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Mono<String> productIdMono = Mono.just(request.pathVariable("productId"));

        return productIdMono.flatMap(productId -> {
            return getProductByIdUseCase.getById(productId)
                    .flatMap(product -> {
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(product);
                    })
                    .onErrorResume(error -> errorHandler.handleServiceError(error, productId));
        });

    }

}
