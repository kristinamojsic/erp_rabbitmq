package com.example.parabbitmq.services;

import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/products")
    List<Product> all()
    {
        return productRepository.findAll();
    }

}
