package com.example.parabbitmq.services;

import com.example.parabbitmq.RabbitMQConfigurator;
import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.messaging.ProductEvent;
import com.example.parabbitmq.repositories.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    //events

    public Product addProduct(Product product)
    {
        productRepository.save(product);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
                "products.events.create", ProductEvent.createNewProduct(product));
        return product;
    }

    public Product updateProductState(long productId,int quantity)
    {
        Product product = productRepository.findById(productId).get();
        product.setQuantity(quantity);
        productRepository.save(product);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
                "products.events.updateState", ProductEvent.updateStateOfProduct(product));
        return product;
    }
    public Product updateProductPrice(long productId,double price)
    {
        Product product = productRepository.findById(productId).get();
        product.setPurchasePrice(price);
        productRepository.save(product);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
                "products.events.updatePrice", ProductEvent.updateStateOfProduct(product));
        return product;
    }


}
