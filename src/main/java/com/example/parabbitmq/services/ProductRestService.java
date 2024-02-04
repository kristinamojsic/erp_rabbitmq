package com.example.parabbitmq.services;

import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProductRestService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public List<Product> all()
    {
        return productRepository.findAll();
    }
    @PostMapping("/product/add")
    public Product addProduct(@RequestBody Product product)
    {
        return productService.addProduct(product);
    }
    @PutMapping("/product/{id}/updateState")
    public Product updateState(@RequestBody Map<String, Integer> request, @PathVariable long id) throws Exception
    {
        return productService.updateProductState(id,request.get("quantity"));
    }

    @PutMapping("product/{id}/updatePrice")
    public Product updatePrice(@RequestBody Map<String, Double> request, @PathVariable long id) throws Exception
    {
        return productService.updateProductPrice(id,request.get("price"));
    }


}
