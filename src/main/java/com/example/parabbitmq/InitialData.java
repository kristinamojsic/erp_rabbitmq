package com.example.parabbitmq;

import com.example.parabbitmq.data.*;
import com.example.parabbitmq.repositories.*;
import com.example.parabbitmq.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitialData {
    private static final Logger log = LoggerFactory.getLogger(InitialData.class);
    @Bean
    public CommandLineRunner demo(ProductRepository productRepository, OrderRepository orderRepository, AccountingRepository accountingRepository, InvoiceRepository invoiceRepository, OrderProductRepository orderProductRepository, ProductService productService,ArticleWarehouseRepository articleWarehouseRepository,WarehouseRepository warehouseRepository)
    {

        return(args -> {
            Product product = new Product("product1","ml");
            Product product2 = new Product("product2","ml");
            productRepository.save(product);
            productRepository.save(product2);
           // double pricePerUnit = product.getPurchasePrice()*1.2;
            ArticleWarehouse articleWarehouse = new ArticleWarehouse(product,35.5);
            ArticleWarehouse articleWarehouse1 = new ArticleWarehouse(product2,35.5);
            articleWarehouseRepository.save(articleWarehouse);
            articleWarehouseRepository.save(articleWarehouse1);
            Warehouse warehouse = new Warehouse(1,articleWarehouse,5,1);
            Warehouse warehouse1 = new Warehouse(1,articleWarehouse1,5,1);
            warehouseRepository.save(warehouse);
            warehouseRepository.save(warehouse1);
            OrderProduct orderProduct = new OrderProduct(product,35.0,0.4,5,5*(35.0+0.4));
            //double pricePerUnit2 = product2.getPurchasePrice()*1.2;
            OrderProduct orderProduct2 = new OrderProduct(product2,35.0,0.4,4,4*(35.0+0.4));
            orderProductRepository.save(orderProduct);
            orderProductRepository.save(orderProduct2);
            List<OrderProduct> productList = new ArrayList<>();
            productList.add(orderProduct);
            productList.add(orderProduct2);
            Order o = new Order(1,1,"kristina",productList);
            orderRepository.save(o);
            Accounting accounting = new Accounting(o,LocalDate.now(),orderProduct.getTotalPrice()+orderProduct2.getTotalPrice());
            accountingRepository.save(accounting);
            Invoice invoice = new Invoice(accounting,LocalDate.now());
            invoiceRepository.save(invoice);
//Informacije o
//artiklima se na početku dobijaju iz servisa modula Roba, getAllProducts.
            log.info("Svi artikli");
            StringBuilder sb = new StringBuilder();
            sb.append("All products:\n");
            List<Product> products = productService.getAllProducts();
            for(Product p : products)
            {
                sb.append(" ");
                sb.append(p);
            }
            log.info(sb.toString());


        });
    }
}
