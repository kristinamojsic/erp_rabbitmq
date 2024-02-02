package com.example.parabbitmq.repositories;

import com.example.parabbitmq.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    void flush();

    List<Product> findAll();

    Product save(Product entity);
}
