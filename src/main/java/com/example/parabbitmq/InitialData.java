package com.example.parabbitmq;

import com.example.parabbitmq.data.*;
import com.example.parabbitmq.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class InitialData {

    @Bean
    public CommandLineRunner demo(ProductRepository productRepository, OrderRepository orderRepository, AccountingRepository accountingRepository, InvoiceRepository invoiceRepository, OrderProductRepository orderProductRepository)
    {

        return(args -> {
            Product product = new Product("product1",35.5,"ml");
            Product product2 = new Product("product2",35.5,"ml");
            productRepository.save(product);
            productRepository.save(product2);
            double pricePerUnit = product.getPurchasePrice()*1.2;

            OrderProduct orderProduct = new OrderProduct(product,pricePerUnit,0.4,5,5*pricePerUnit+0.4);
            double pricePerUnit2 = product2.getPurchasePrice()*1.2;
            OrderProduct orderProduct2 = new OrderProduct(product2,pricePerUnit2,0.4,4,4*pricePerUnit2+0.4);
            orderProductRepository.save(orderProduct);
            orderProductRepository.save(orderProduct2);
            List<OrderProduct> productList = new ArrayList<>();
            productList.add(orderProduct);
            productList.add(orderProduct2);
            Order o = new Order(1,1,"kristina",productList);
            orderRepository.save(o);
            Accounting accounting = new Accounting(o,LocalDate.now());
            accountingRepository.save(accounting);
            Invoice invoice = new Invoice(accounting,LocalDate.now());
            invoiceRepository.save(invoice);


        });
    }
}
