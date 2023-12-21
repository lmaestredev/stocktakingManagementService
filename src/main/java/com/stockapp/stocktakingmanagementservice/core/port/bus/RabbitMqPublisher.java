package com.stockapp.stocktakingmanagementservice.core.port.bus;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
public class RabbitMqPublisher {

    @Value("${rabbitExchange}")
    private String EXCHANGE_NAME;

    @Value("${customerRoutingKey}")
    private String CUSTOMER_ROUTING_KEY;

    @Value("${productRoutingKey}")
    private String PRODUCT_ROUTING_KEY;

    @Value("${saleRoutingKey}")
    private String SALE_ROUTING_KEY;

    @Value("${errorRoutingKey}")
    private String ERROR_ROUTING_KEY;

    @Value("${retailRoutingKey}")
    private String RETAIL_ROUTING_KEY;

    @Value("${whosaleRoutingKey}")
    private String WHOSALE_ROUTING_KEY;

    @Autowired
    private Sender sender;

    @Autowired
    private Gson gson;

    public void publishCustomer(Object object) {
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        CUSTOMER_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }

    public void publishProduct(Object object) {
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        PRODUCT_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }

    public void publishSale(Object object) {
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        SALE_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
        ;
    }

    public void publishErrorMessage(Object object) {
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        ERROR_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }

    public void publishRetail(Object object) {
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        RETAIL_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }

    public void publishWhosale(Object object) {
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        WHOSALE_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }
}
