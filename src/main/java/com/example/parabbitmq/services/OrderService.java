package com.example.parabbitmq.services;

import com.example.parabbitmq.data.Order;
import com.example.parabbitmq.data.OrderProduct;
import com.example.parabbitmq.repositories.OrderProductRepository;
import com.example.parabbitmq.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderService(OrderRepository orderRepository,OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public String addOrder(Order order)
    {
        //dodati logiku izvrsavanja narudzbine
        this.orderRepository.save(order);
        for(OrderProduct orderProduct : order.getProductList())
        {
            orderProduct.setOrder(order);
            this.orderProductRepository.save(orderProduct);

        }
        return "uspesno";
    }
}
