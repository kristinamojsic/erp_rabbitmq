package com.example.parabbitmq;


import com.example.parabbitmq.messaging.ProductEventReportingService;
import com.example.parabbitmq.messaging.ReservationListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurator{
    public static final String PRODUCTS_TOPIC_EXCHANGE_NAME = "products-events-exchange";
    public static final String ORDERS_TOPIC_EXCHANGE_NAME = "orders-events-exchange";
    public static final String PRODUCTS_SERVICE_QUEUE = "products-service-queue";
    public static final String RESERVATION_QUEUE = "reservation-queue";
    public static final String RESERVATION_FAILURE_QUEUE = "reservation-failure-queue";
    public static final String INVOICE_CONFIRMATION_QUEUE = "invoice-confirmation-queue";


    @Bean
    Queue productQueue() {
        return new Queue(PRODUCTS_SERVICE_QUEUE, false);
    }
    @Bean
    Queue reservationQueue() {
        return new Queue(RESERVATION_QUEUE, false);
    }
    @Bean
    Queue reservationFailureQueue() {
        return new Queue(RESERVATION_FAILURE_QUEUE, false);
    }
    @Bean
    Queue invoiceConfirmationQueue() {
        return new Queue(INVOICE_CONFIRMATION_QUEUE, false);
    }
    @Bean
    TopicExchange productExchange() {
        return new TopicExchange(PRODUCTS_TOPIC_EXCHANGE_NAME);
    }
    @Bean
    TopicExchange ordersExchange() {
        return new TopicExchange(ORDERS_TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding productBinding(Queue productQueue, TopicExchange productExchange) {
        return BindingBuilder.bind(productQueue).to(productExchange).with("products.events.#");
    }
    @Bean
    Binding reservationBinding(Queue reservationQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(reservationQueue).to(ordersExchange).with("reservation.#");
    }

    @Bean
    Binding reservationFailureBinding(Queue reservationFailureQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(reservationFailureQueue).to(ordersExchange).with("reservation.failure.#");
    }

    @Bean
    Binding invoiceConfirmationBinding(Queue invoiceConfirmationQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(invoiceConfirmationQueue).to(ordersExchange).with("invoice.confirmation.#");
    }
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(PRODUCTS_SERVICE_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    @Bean
    SimpleMessageListenerContainer reservationListenerContainer(ConnectionFactory connectionFactory,
                                                                MessageListenerAdapter reservationListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RESERVATION_QUEUE);
        container.setMessageListener(reservationListenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ProductEventReportingService receiver, MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "receiveMessage");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }

    @Bean
    MessageListenerAdapter reservationListenerAdapter(ReservationListener reservationListener, MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(reservationListener, "processReservation");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }

    @Bean
    public MessageConverter jsonToMapMessageConverter() {
        DefaultClassMapper defaultClassMapper = new DefaultClassMapper();
        defaultClassMapper.setTrustedPackages("*");
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        jackson2JsonMessageConverter.setClassMapper(defaultClassMapper);
        return jackson2JsonMessageConverter;
    }

}
