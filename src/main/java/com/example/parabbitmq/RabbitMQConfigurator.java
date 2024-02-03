package com.example.parabbitmq;


import com.example.parabbitmq.messaging.MessagingReportingService;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurator {
    public static final String PRODUCTS_TOPIC_EXCHANGE_NAME = "products-events-exchange";

    public static final String PRODUCTS_SERVICE_QUEUE = "products-service-queue";

    @Bean
    Queue queue() {
        return new Queue(PRODUCTS_SERVICE_QUEUE, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(PRODUCTS_TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("products.events.#");
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
    MessageListenerAdapter listenerAdapter(MessagingReportingService receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
