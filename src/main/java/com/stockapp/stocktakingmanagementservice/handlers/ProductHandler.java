package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.RabbitPublisherDto;
import com.stockapp.stocktakingmanagementservice.core.dtos.request.ProductDtoReq;
import com.stockapp.stocktakingmanagementservice.core.port.bus.RabbitMqPublisher;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.*;
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
    private final GetPaginatedProductsUseCase getPaginatedProductsUseCase;
    private final CreateProductsByLotsUseCase createProductsByLotsUseCase;
    private final ErrorHandler errorHandler;
    private final RabbitMqPublisher rabbitMqPublisher;


    @Autowired
    public ProductHandler(CreateProductUseCase createProductUseCase, GetAllProductsUseCase getAllProductsUseCase, GetProductByIdUseCase getProductByIdUseCase, GetPaginatedProductsUseCase getPaginatedProductsUseCase, CreateProductsByLotsUseCase createProductsByLotsUseCase, ErrorHandler errorHandler, RabbitMqPublisher rabbitMqPublisher) {
        this.createProductUseCase = createProductUseCase;
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.getProductByIdUseCase = getProductByIdUseCase;
        this.getPaginatedProductsUseCase = getPaginatedProductsUseCase;
        this.createProductsByLotsUseCase = createProductsByLotsUseCase;
        this.errorHandler = errorHandler;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ProductDtoReq.class).flatMap(productDto -> {
            return createProductUseCase.create(productDto)
                    .flatMap(created -> {
                        rabbitMqPublisher.publishProduct(new RabbitPublisherDto("createProductUseCase", created));
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(created);
                    })
                    .onErrorResume(error -> {
                        rabbitMqPublisher.publishErrorMessage(new RabbitPublisherDto("createProductUseCase", productDto, error.getMessage()));
                        return errorHandler.handleServiceError(error, productDto);
                    });
        });
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return getAllProductsUseCase.getAll()
                .collectList()
                .flatMap(products -> {
                    rabbitMqPublisher.publishProduct(new RabbitPublisherDto("getAllProductsUseCase", products));
                    return ServerResponse.ok().bodyValue(products);
                })
                .onErrorResume(error -> {
                    rabbitMqPublisher.publishErrorMessage(new RabbitPublisherDto("getAllProductsUseCase", "", error.getMessage()));
                    return errorHandler.handleServiceError(error, "");
                });
    }

    public Mono<ServerResponse> getById(ServerRequest request) {
        Mono<String> productIdMono = Mono.just(request.pathVariable("productId"));

        return productIdMono.flatMap(productId -> {
            return getProductByIdUseCase.getById(productId)
                    .flatMap(product -> {
                        rabbitMqPublisher.publishProduct(new RabbitPublisherDto("getCustomerByIdUseCase", product));
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(product);
                    })
                    .onErrorResume(error -> {
                        rabbitMqPublisher.publishErrorMessage(new RabbitPublisherDto("getCustomerByIdUseCase", productId, error.getMessage()));
                        return errorHandler.handleServiceError(error, productId);
                    });
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
