package com.example.parabbitmq.services;

import com.example.parabbitmq.data.Order;
import com.example.parabbitmq.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Order> all() {
        return orderRepository.findAll();
    }

    @PostMapping("/orders/add")
    public ResponseEntity<String> addOrder(@RequestBody Order order) {
        try{
             orderService.addOrder(order);
             return ResponseEntity.ok("Order added.");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding order: " + e.getMessage());
        }
    }

    @PostMapping("/pay/{id}")
    public ResponseEntity<String> addInvoice(@PathVariable long id, @RequestBody Map<String,Double> totalPrice) {
        try {
            orderService.addInvoice(id, totalPrice.get("totalPrice"));
            return ResponseEntity.ok("Invoice added.");
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding invoice: " + e.getMessage());
        }

    }
}
