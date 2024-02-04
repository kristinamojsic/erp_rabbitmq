package com.example.parabbitmq.services;

import com.example.parabbitmq.data.Order;
import com.example.parabbitmq.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderRestService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderService orderService;
    @GetMapping("/orders")
    public List<Order> all()
    {
        return orderRepository.findAll();
    }

    @PostMapping("/orders/add")
    public String addOrder(@RequestBody Order order)
    {
        return orderService.addOrder(order);
    }
}
