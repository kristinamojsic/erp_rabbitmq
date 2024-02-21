package com.example.parabbitmq;

import com.example.parabbitmq.prodaja.data.Accounting;
import com.example.parabbitmq.prodaja.data.Order;
import com.example.parabbitmq.prodaja.data.OrderProduct;
import com.example.parabbitmq.prodaja.repositories.AccountingRepository;
import com.example.parabbitmq.prodaja.repositories.InvoiceRepository;
import com.example.parabbitmq.prodaja.repositories.OrderProductRepository;
import com.example.parabbitmq.prodaja.repositories.OrderRepository;
import com.example.parabbitmq.prodaja.services.OrderService;
import com.example.parabbitmq.roba.data.ArticleWarehouse;
import com.example.parabbitmq.roba.data.Product;
import com.example.parabbitmq.roba.data.Reservation;
import com.example.parabbitmq.roba.data.Warehouse;
import com.example.parabbitmq.roba.repositories.ArticleWarehouseRepository;
import com.example.parabbitmq.roba.repositories.ProductRepository;
import com.example.parabbitmq.roba.repositories.ReservationRepository;
import com.example.parabbitmq.roba.repositories.WarehouseRepository;
import com.example.parabbitmq.roba.services.ProductService;
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
    public CommandLineRunner demo(ProductRepository productRepository, OrderRepository orderRepository, AccountingRepository accountingRepository, InvoiceRepository invoiceRepository, OrderProductRepository orderProductRepository, ProductService productService, ArticleWarehouseRepository articleWarehouseRepository, WarehouseRepository warehouseRepository, ReservationRepository reservationRepository, OrderService orderService)
    {

        return(args -> {
            List<Product> productList = new ArrayList<>();
            productList.add(new Product("product1","ml"));
            productList.add(new Product("product2","ml"));
            productList.add(new Product("product3","g"));
            productList.add(new Product("product4","g"));
            for(Product product:productList) {
                productRepository.save(product);
            }
            List<ArticleWarehouse> articleWarehouseList = new ArrayList<>();
            for(int i = 0; i < productList.size(); i++){
                articleWarehouseList.add(new ArticleWarehouse(productList.get(i),30.0+i*10));
                articleWarehouseRepository.save(articleWarehouseList.get(i));
            }
            List<Warehouse> warehouses = new ArrayList<>();
            LocalDate date = LocalDate.now();
            for(int i = 1; i<3 ; i++) {
                for(int j = 0; j < articleWarehouseList.size(); j++) {
                    warehouses.add(new Warehouse(i, articleWarehouseList.get(j), 5 + i + j, 1, date));
                    warehouseRepository.save(warehouses.get(j));
                }
            }

            OrderProduct orderProduct = new OrderProduct(productList.get(0),articleWarehouseList.get(0).getPurchasePrice()*1.2,0.4,5);
            OrderProduct orderProduct2 = new OrderProduct(productList.get(1),articleWarehouseList.get(1).getPurchasePrice()*1.2,0.4,5);
            orderProductRepository.save(orderProduct);
            orderProductRepository.save(orderProduct2);
            List<OrderProduct> orderProductList = new ArrayList<>();
            orderProductList.add(orderProduct);
            orderProductList.add(orderProduct2);
            Order o = new Order(1,1,"kristina",orderProductList);
            orderRepository.save(o);
            for(OrderProduct op : orderProductList){
                op.setOrder(o);
                orderProductRepository.save(op);
            }

            Accounting accounting = new Accounting(o,LocalDate.now(),orderProduct.getTotalPrice()+orderProduct2.getTotalPrice());
            accountingRepository.save(accounting);
            //Invoice invoice = new Invoice(accounting,LocalDate.now());
            //invoiceRepository.save(invoice);
            Reservation reservation = new Reservation(productList.get(0),5,o);
            reservationRepository.save(reservation);
            Reservation reservation2 = new Reservation(productList.get(1),5,o);
            reservationRepository.save(reservation2);

            StringBuilder sb = new StringBuilder();
            sb.append("All products:\n");
            List<Product> products = productService.getAllProducts();
            for(Product p : products) {
                sb.append(" ").append(p);
            }
            log.info(sb.toString());
            orderService.checkAccountings();
        });
    }
}
