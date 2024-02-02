package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAll();

    Order save(Order entity);
}
