package com.example.parabbitmq;


import com.example.parabbitmq.messaging.*;
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
    public static final String ORDERS2_TOPIC_EXCHANGE_NAME = "orders2-events-exchange";
    public static final String SOLD_TOPIC_EXCHANGE_NAME = "sold-events-exchange";
    public static final String CANCELRESERVATION_TOPIC_EXCHANGE_NAME = "cancel-reservation-exchange";
    public static final String PRODUCTS_SERVICE_QUEUE = "products-service-queue";
    public static final String RESERVATION_QUEUE = "reservation-queue";
    public static final String RESERVATION_RESPONSE_QUEUE = "reservation-response-queue";
    public static final String CANCEL_RESERVATION_QUEUE = "cancelreservation-queue";
    public static final String SOLDPRODUCTS_SERVICE_QUEUE = "soldproducts-service-queue";
//poruka ponistavanje rezervacije - od modula prodaja ka robi
    //poruka prodata roba - od modula prodaja ka robi


//queues
    @Bean
    Queue productQueue() {
        return new Queue(PRODUCTS_SERVICE_QUEUE, false);
    }
    @Bean
    Queue reservationQueue() {
        return new Queue(RESERVATION_QUEUE, false);
    }
    @Bean
    Queue reservationResponseQueue() {
        return new Queue(RESERVATION_RESPONSE_QUEUE, false);
    }
    @Bean
    Queue soldProductsQueue() {
        return new Queue(SOLDPRODUCTS_SERVICE_QUEUE, false);
    }
    @Bean
    Queue cancelReservationQueue() {
        return new Queue(CANCEL_RESERVATION_QUEUE, false);
    }
//exchanges
    @Bean
    TopicExchange productExchange() {
        return new TopicExchange(PRODUCTS_TOPIC_EXCHANGE_NAME);
    }
    @Bean
    TopicExchange ordersExchange() {
        return new TopicExchange(ORDERS_TOPIC_EXCHANGE_NAME);
    }
    @Bean
    TopicExchange orders2Exchange() {
        return new TopicExchange(ORDERS2_TOPIC_EXCHANGE_NAME);
    }
    @Bean
    TopicExchange soldProductsExchange() {
        return new TopicExchange(SOLD_TOPIC_EXCHANGE_NAME);
    }
    @Bean
    TopicExchange cancelReservationExchange() {
        return new TopicExchange(CANCELRESERVATION_TOPIC_EXCHANGE_NAME);
    }
//bindings
    //"products.events.#" routing key
    @Bean
    Binding productBinding(Queue productQueue, TopicExchange productExchange) {
        return BindingBuilder.bind(productQueue).to(productExchange).with("products.events.#");
    }
    @Bean
    Binding reservationBinding(Queue reservationQueue, TopicExchange ordersExchange) {
        return BindingBuilder.bind(reservationQueue).to(ordersExchange).with("reservation.#");
    }

    @Bean
    Binding reservationResponseBinding(Queue reservationResponseQueue, TopicExchange orders2Exchange) {
        return BindingBuilder.bind(reservationResponseQueue).to(orders2Exchange).with("reservation.response.#");
    }

    @Bean
    Binding soldProductsBinding(Queue soldProductsQueue, TopicExchange soldProductsExchange) {
        return BindingBuilder.bind(soldProductsQueue).to(soldProductsExchange).with("soldproducts.#");
    }
    @Bean
    Binding cancelReservationBinding(Queue cancelReservationQueue, TopicExchange cancelReservationExchange) {
        return BindingBuilder.bind(cancelReservationQueue).to(cancelReservationExchange).with("cancelreservation.#");
    }
//containers
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
    SimpleMessageListenerContainer reservationResponseListenerContainer(ConnectionFactory connectionFactory,
                                                                MessageListenerAdapter reservationResponseListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RESERVATION_RESPONSE_QUEUE);
        container.setMessageListener(reservationResponseListenerAdapter);
        return container;
    }
    @Bean
    SimpleMessageListenerContainer soldProductsListenerContainer(ConnectionFactory connectionFactory,
                                                                        MessageListenerAdapter soldProductsListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(SOLDPRODUCTS_SERVICE_QUEUE);
        container.setMessageListener(soldProductsListenerAdapter);
        return container;
    }
    @Bean
    SimpleMessageListenerContainer cancelReservationListenerContainer(ConnectionFactory connectionFactory,
                                                                 MessageListenerAdapter cancelReservationListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(CANCEL_RESERVATION_QUEUE);
        container.setMessageListener(cancelReservationListenerAdapter);
        return container;
    }
//listener adapters
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
    MessageListenerAdapter reservationResponseListenerAdapter(ReservationResponseListener reservationResponseListener, MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(reservationResponseListener, "processReservationResponse");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }
    @Bean
    MessageListenerAdapter soldProductsListenerAdapter(SoldProductsListener soldProductsListener, MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(soldProductsListener, "processSoldProductsMessage");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }
    @Bean
    MessageListenerAdapter cancelReservationListenerAdapter(CancelReservationListener cancelReservationListener, MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(cancelReservationListener, "cancelReservation");
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
