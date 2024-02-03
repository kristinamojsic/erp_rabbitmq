package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
    void flush();

    List<OrderProduct> findAll();

    OrderProduct save(OrderProduct entity);
}
