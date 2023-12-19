package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.ProductDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.ErrorResponse;
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

    @Autowired
    public ProductHandler(CreateProductUseCase createProductUseCase, GetAllProductsUseCase getAllProductsUseCase, GetProductByIdUseCase getProductByIdUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
//        Mono<ProductDtoReq> productDtoMono = request.bodyToMono(ProductDtoReq.class);

        return request.bodyToMono(ProductDtoReq.class).flatMap(productDto -> {
            return createProductUseCase.create(productDto)
                    .flatMap(created -> {
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(created);
                    })
                    .onErrorResume(error -> handleServiceError(error, productDto));
        });
    }

    public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
        Flux<ProductDtoRes> products = getAllProductsUseCase.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, ProductDtoRes.class);

    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        String productId = request.pathVariable("productId");

        return getProductByIdUseCase.getById(productId).flatMap(created -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(created)
                    .switchIfEmpty(ServerResponse.notFound().build());
        });

    }

    private Mono<ServerResponse> handleServiceError(Throwable error, Object data) {
        ErrorResponse errorResponse = new ErrorResponse(error.getMessage(), data);
        return ServerResponse.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(errorResponse);
    }
}
