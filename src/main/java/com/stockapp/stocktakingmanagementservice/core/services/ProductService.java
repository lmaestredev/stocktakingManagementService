package com.stockapp.stocktakingmanagementservice.core.services;

import com.stockapp.stocktakingmanagementservice.core.dtos.request.ProductDtoReq;
import com.stockapp.stocktakingmanagementservice.core.dtos.response.ProductDtoRes;
import com.stockapp.stocktakingmanagementservice.core.models.Product;
import com.stockapp.stocktakingmanagementservice.core.port.ProductRepository;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.CreateProductUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.GetAllProductsUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.product.GetProductByIdUseCase;
import com.stockapp.stocktakingmanagementservice.utils.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService implements CreateProductUseCase, GetAllProductsUseCase, GetProductByIdUseCase {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Mono<ProductDtoRes> create(ProductDtoReq productDtoReq) {
        Product product = productMapper.dtoToEntity(productDtoReq);
        return productRepository.existsByName(product.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RuntimeException("El producto ya existe en la base de datos."));
                    } else {
                        if (product.getName() == null) {
                            return Mono.error(new RuntimeException("Nombre no puede estar vacio o ser nulo"));
                        } else if (product.getName().isEmpty()) {
                            return Mono.error(new RuntimeException("Nombre no puede estar vacio o ser nulo"));
                        } else {
                            return productRepository.save(product)
                                    .map(productMapper::entityToDtoRes);

                        }
                    }
                });
    }

    @Override
    public Flux<ProductDtoRes> getAll() {
        return productRepository.findAll()
                .collectList()
                .flatMapMany(products -> {
                    if (products.isEmpty()) {
                        return Mono.error(new RuntimeException("La lista está vacia"));
                    } else {
                        return Flux.fromIterable(products).map(productMapper::entityToDtoRes);
                    }
                });
    }

    @Override
    public Mono<ProductDtoRes> getById(String id) {
        return productRepository.findById(id)
                .flatMap(product -> Mono.just(productMapper.entityToDtoRes(product)))
                .switchIfEmpty(Mono.error(new RuntimeException("El producto no existe")));
    }
}
