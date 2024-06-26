package com.example.parabbitmq.roba.services;

import com.example.parabbitmq.RabbitMQConfigurator;
import com.example.parabbitmq.messaging.ProductEvent;
import com.example.parabbitmq.roba.data.ArticleWarehouse;
import com.example.parabbitmq.roba.data.Product;
import com.example.parabbitmq.roba.data.Warehouse;
import com.example.parabbitmq.roba.repositories.ProductRepository;
import com.example.parabbitmq.roba.repositories.ReservationRepository;
import com.example.parabbitmq.roba.repositories.WarehouseRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    //events

    public Product addProduct(Product product) {
        productRepository.save(product);
        ProductEvent productEvent = ProductEvent.createNewProduct(product);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
                "products.events.create",productEvent);
        return product;
    }

    public void receptionOfProducts(int supplierId, int warehouseId, int quantity, List<ArticleWarehouse> articles) {
        LocalDate date = LocalDate.now();
        List<Product> products = new ArrayList<>();

        for(ArticleWarehouse article : articles) {
            products.add(article.getProduct());
            Warehouse warehouse = new Warehouse(warehouseId,article,quantity,supplierId,date);
            warehouseRepository.save(warehouse);
        }

        ProductEvent productEvent = ProductEvent.updateStateOfProduct(products);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME,
               "products.events.updateState",productEvent);
    }


    public String getProductData(long productId) {

        StringBuilder sb = new StringBuilder();
        Product product = productRepository.findById(productId).orElseThrow();
        sb.append("product\n").append(product).append("\n");

        Optional<Integer> quantity = warehouseRepository.findTotalQuantityByProductId(productId);
        Optional<Integer> reservedQuantity = reservationRepository.findTotalReservedQuantityByProductId(productId);
        int totalQauntity = reservedQuantity.isPresent() ? quantity.get() - reservedQuantity.get() : quantity.get();
        sb.append("total quantity: ").append(totalQauntity).append("\n");

        List<Warehouse> warehousePurchase = warehouseRepository.findStateOfWarehousesForProductId(productId);
        for(Warehouse w : warehousePurchase) {
            sb.append("purchasePrice ").append(w.getProduct().getPurchasePrice())
                    .append(", supplierId ").append(w.getSupplierId())
                    .append(", date").append(w.getDate()).append("\n");
        }
        return sb.toString();
    }

    public String getProductState(long productId) {
        StringBuilder sb = new StringBuilder();
        Product product = productRepository.findById(productId).orElseThrow();
        sb.append("Warehouse id | quantity\n");
        List<Object[]> result = warehouseRepository.findQuantityForProductIdGroupByWarehouse(productId);

        for(Object[] o : result) {
            sb.append(o[0]).append(" | ").append(o[1]).append("\n");
        }
        return sb.toString();
    }
}
