package com.example.parabbitmq.services;

import com.example.parabbitmq.data.Invoice;
import com.example.parabbitmq.data.Order;
import com.example.parabbitmq.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public Order addOrder(@RequestBody Order order)
    {
        return orderService.addOrder(order);
    }

    @PostMapping("/pay/{id}")
    public Invoice addInvoice(@PathVariable long id, @RequestBody Map<String,Double> totalPrice)
    {
        try
        {
            return orderService.addInvoice(id, totalPrice.get("totalPrice"));
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }

    }
}
