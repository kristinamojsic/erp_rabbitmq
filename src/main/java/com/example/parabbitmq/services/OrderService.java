package com.example.parabbitmq.services;

import com.example.parabbitmq.RabbitMQConfigurator;
import com.example.parabbitmq.data.Accounting;
import com.example.parabbitmq.data.Order;
import com.example.parabbitmq.data.OrderProduct;
import com.example.parabbitmq.messaging.ReservationMessage;
import com.example.parabbitmq.repositories.OrderProductRepository;
import com.example.parabbitmq.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;
    public OrderService(OrderRepository orderRepository,OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }
    @Transactional
    public Order addOrder(Order order)
    {

        this.orderRepository.save(order);
        double totalPrice = 0.0;
        LocalDate dateOfPayment = LocalDate.now().plusDays(5);
        List<OrderProduct> productList = order.getProductList();
        for(OrderProduct orderProduct : productList)
        {
            orderProduct.setOrder(order);
            this.orderProductRepository.save(orderProduct);
            totalPrice += orderProduct.getTotalPrice();

        }

        Accounting tmpAccounting = new Accounting(order,dateOfPayment,totalPrice);

        ReservationMessage reservationMessage = new ReservationMessage(productList);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.ORDERS_TOPIC_EXCHANGE_NAME,
                "reservation.queue", reservationMessage);
        //poruka rezervacija robe

        return order;
    }
}
