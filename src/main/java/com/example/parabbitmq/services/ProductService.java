package com.example.parabbitmq.services;

import com.example.parabbitmq.RabbitMQConfigurator;
import com.example.parabbitmq.data.ArticleWarehouse;
import com.example.parabbitmq.data.Product;
import com.example.parabbitmq.data.Warehouse;
import com.example.parabbitmq.messaging.ProductEvent;
import com.example.parabbitmq.repositories.ArticleWarehouseRepository;
import com.example.parabbitmq.repositories.ProductRepository;
import com.example.parabbitmq.repositories.WarehouseRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ArticleWarehouseRepository articleWarehouseRepository;
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
//u tabeli informacije o tome koliko kog artikla je pristiglo u neko skladiste
    public void receptionOfProducts(int supplierId, int warehouseId, int quantity, List<ArticleWarehouse> articles)
    {
        LocalDate date = LocalDate.now();
        List<Product> products = new ArrayList<>();
        for(ArticleWarehouse article : articles)
        {
            products.add(article.getProduct());
            if(articleWarehouseRepository.findById(article.getId())==null)
            {
                articleWarehouseRepository.save(article);
            }

            Warehouse warehouse = new Warehouse(warehouseId,article,quantity,supplierId,date);
            warehouseRepository.save(warehouse);
        }
        ProductEvent productEvent = ProductEvent.updateStateOfProduct(products);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
                "products.events.updateState",productEvent);

    }
    
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
