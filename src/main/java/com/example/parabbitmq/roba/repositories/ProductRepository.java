package com.example.parabbitmq.roba.repositories;

import com.example.parabbitmq.roba.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    void flush();

    List<Product> findAll();

    Product save(Product entity);

    Optional<Product> findById(Long aLong);
}
