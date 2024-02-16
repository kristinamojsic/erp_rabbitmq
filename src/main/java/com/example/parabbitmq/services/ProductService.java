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
        ProductEvent productEvent = ProductEvent.createNewProduct(product);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
                "products.events.create",productEvent);
        return product;
    }

    /*public Product updateProductState(long productId,int quantity)
    {
        Product product = productRepository.findById(productId).get();

        product.setQuantity(quantity);
        productRepository.save(product);
        ProductEvent productEvent = ProductEvent.updateStateOfProduct(product);

        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
                "products.events.updateState",productEvent);
        return product;
    }*/
    /*public Product updateProductPrice(long productId,double price) throws Exception
    {
        Product product = productRepository.findById(productId).get();

        product.setPurchasePrice(price);
        productRepository.save(product);

        ProductEvent productEvent = ProductEvent.updatePriceOfProduct(product);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
                "products.events.updatePrice",productEvent);
        return product;
    }*/


}
