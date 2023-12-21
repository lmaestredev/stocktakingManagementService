package com.stockapp.stocktakingmanagementservice.config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RabbitConfig {

    @Value("${rabbitUri}")
    private String URI_NAME;

    @Value("${rabbitExchange}")
    private String EXCHANGE_NAME;

    //routers key (una por cada cola)
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

    //queues
    @Value("${customerQueue}")
    private String CUSTOMER_QUEUE;

    @Value("${productQueue}")
    private String PRODUCT_QUEUE;

    @Value("${saleQueue}")
    private String SALE_QUEUE;

    @Value("${errorQueue}")
    private String ERROR_QUEUE;

    @Value("${retailQueue}")
    private String RETAIL_QUEUE;

    @Value("${whosaleQueue}")
    private String WHOSALE_QUEUE;

    @Bean
    public AmqpAdmin amqpAdmin() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(URI.create(URI_NAME));
        var amqpAdmin = new RabbitAdmin(connectionFactory);

        var exchange = new TopicExchange(EXCHANGE_NAME);
        var clientQueue = new Queue(CUSTOMER_QUEUE, true, false, false);
        var accountQueue = new Queue(PRODUCT_QUEUE, true, false, false);
        var saleQueue = new Queue(SALE_QUEUE, true, false, false);
        var errorQueue = new Queue(ERROR_QUEUE, true, false, false);
        var retailQueue = new Queue(RETAIL_QUEUE, true, false, false);
        var whosaleQueue = new Queue(WHOSALE_QUEUE, true, false, false);

        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(clientQueue);
        amqpAdmin.declareQueue(accountQueue);
        amqpAdmin.declareQueue(saleQueue);
        amqpAdmin.declareQueue(errorQueue);
        amqpAdmin.declareQueue(retailQueue);
        amqpAdmin.declareQueue(whosaleQueue);


        amqpAdmin.declareBinding(BindingBuilder.bind(clientQueue).to(exchange).with(CUSTOMER_ROUTING_KEY));
        amqpAdmin.declareBinding(BindingBuilder.bind(accountQueue).to(exchange).with(PRODUCT_ROUTING_KEY));
        amqpAdmin.declareBinding(BindingBuilder.bind(saleQueue).to(exchange).with(SALE_ROUTING_KEY));
        amqpAdmin.declareBinding(BindingBuilder.bind(errorQueue).to(exchange).with(ERROR_ROUTING_KEY));
        amqpAdmin.declareBinding(BindingBuilder.bind(retailQueue).to(exchange).with(RETAIL_ROUTING_KEY));
        amqpAdmin.declareBinding(BindingBuilder.bind(whosaleQueue).to(exchange).with(WHOSALE_ROUTING_KEY));

        return amqpAdmin;
    }

    @Bean
    public ConnectionFactory connectionFactory() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();
        connectionFactory.setUri(URI_NAME);
        return connectionFactory;
    }

    @Bean
    public Mono<Connection> connectionMono(@Value("spring.application.name") String name, ConnectionFactory connectionFactory) {
        return Mono.fromCallable(() -> connectionFactory.newConnection(name)).cache();
    }

    @Bean
    public SenderOptions senderOptions(Mono<Connection> connectionMono) {
        return new SenderOptions()
                .connectionMono(connectionMono)
                .resourceManagementScheduler(Schedulers.boundedElastic());
    }

    @Bean
    public Sender sender(SenderOptions senderOptions) {
        return RabbitFlux.createSender(senderOptions);
    }


    @Bean
    public ReceiverOptions receiverOptions(Mono<Connection> connectionMono) {
        return new ReceiverOptions()
                .connectionMono(connectionMono);
    }

    @Bean
    public Receiver receiver(ReceiverOptions receiverOptions) {
        return RabbitFlux.createReceiver(receiverOptions);
    }
}
