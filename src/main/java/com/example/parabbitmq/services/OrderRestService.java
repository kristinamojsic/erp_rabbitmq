package com.example.parabbitmq.services;

import com.example.parabbitmq.data.Order;
import com.example.parabbitmq.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestService {
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/orders")
    List<Order> all()
    {
        return orderRepository.findAll();
    }
}
