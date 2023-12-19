package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.ProductDtoReq;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ProductHandler {

    private final CreateProductUseCase createProductUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetPaginatedProductsUseCase getPaginatedProductsUseCase;
    private final CreateProductsByLotsUseCase createProductsByLotsUseCase;
    private final ErrorHandler errorHandler;

    @Autowired
    public ProductHandler(CreateProductUseCase createProductUseCase, GetAllProductsUseCase getAllProductsUseCase, GetProductByIdUseCase getProductByIdUseCase, GetPaginatedProductsUseCase getPaginatedProductsUseCase, CreateProductsByLotsUseCase createProductsByLotsUseCase, ErrorHandler errorHandler) {
        this.createProductUseCase = createProductUseCase;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.getPaginatedProductsUseCase = getPaginatedProductsUseCase;
        this.createProductsByLotsUseCase = createProductsByLotsUseCase;
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

    public Mono<ServerResponse> getPaginated(ServerRequest request) {
        return getPaginatedProductsUseCase.getPaginated(0, 5)
                .collectList()
                .flatMap(
                        productDtoRes -> {
                            return ServerResponse.ok().bodyValue(productDtoRes);
                        }
                );

    }

    public Mono<ServerResponse> createLots(ServerRequest request) {
        Flux<ProductDtoReq> productsFlux = request.bodyToFlux(ProductDtoReq.class);
        return productsFlux
                .collectList().flatMap(productsDtoReq ->
                        createProductsByLotsUseCase.createLots(productsDtoReq)
                                .flatMap(productsCreated ->
                                        ServerResponse.ok().bodyValue(productsCreated)
                                )
                                .onErrorResume(error ->
                                        ServerResponse.badRequest().bodyValue(error.getMessage())
                                )
                );
    }


}
