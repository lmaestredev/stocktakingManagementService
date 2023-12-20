package com.stockapp.stocktakingmanagementservice.handlers;

import com.stockapp.stocktakingmanagementservice.core.dtos.RabbitPublisherDto;
import com.stockapp.stocktakingmanagementservice.core.dtos.request.SaleDtoReq;
import com.stockapp.stocktakingmanagementservice.core.port.bus.RabbitMqPublisher;
import com.stockapp.stocktakingmanagementservice.core.usecase.sale.CreateSaleUseCase;
import com.stockapp.stocktakingmanagementservice.core.usecase.sale.GetAllSalesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class SaleHandler {

    private final CreateSaleUseCase createSaleUseCase;
    private final GetAllSalesUseCase getAllSalesUseCase;
    private final ErrorHandler errorHandler;
    private final RabbitMqPublisher rabbitMqPublisher;


    @Autowired
    public SaleHandler(CreateSaleUseCase createSaleUseCase, GetAllSalesUseCase getAllSalesUseCase, ErrorHandler errorHandler, RabbitMqPublisher rabbitMqPublisher) {
        this.createSaleUseCase = createSaleUseCase;
        this.getAllSalesUseCase = getAllSalesUseCase;
        this.errorHandler = errorHandler;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(SaleDtoReq.class).flatMap(saleDto -> {
            return createSaleUseCase.create(saleDto)
                    .flatMap(created -> {
                        rabbitMqPublisher.publishSale(new RabbitPublisherDto("createSaleUseCase", created));
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(created);
                    })
                    .onErrorResume(error -> {
                        rabbitMqPublisher.publishErrorMessage(new RabbitPublisherDto("createSaleUseCase", saleDto, error.getMessage()));
                        return errorHandler.handleServiceError(error, saleDto);
                    });
        });
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return getAllSalesUseCase.getAll()
                .collectList()
                .flatMap(sales -> {
                    rabbitMqPublisher.publishSale(new RabbitPublisherDto("getAllSalesUseCase", sales));
                    return ServerResponse.ok().bodyValue(sales);
                })
                .onErrorResume(error -> {
                    rabbitMqPublisher.publishErrorMessage(new RabbitPublisherDto("getAllSalesUseCase", "", error.getMessage()));
                    return errorHandler.handleServiceError(error, "");
                });
    }

}
