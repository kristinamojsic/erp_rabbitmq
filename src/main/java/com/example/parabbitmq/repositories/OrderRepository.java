package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAll();

    Order save(Order entity);
}
