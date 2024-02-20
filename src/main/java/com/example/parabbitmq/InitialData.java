package com.example.parabbitmq;

import com.example.parabbitmq.prodaja.data.Accounting;
import com.example.parabbitmq.prodaja.data.Order;
import com.example.parabbitmq.prodaja.data.OrderProduct;
import com.example.parabbitmq.prodaja.repositories.AccountingRepository;
import com.example.parabbitmq.prodaja.repositories.InvoiceRepository;
import com.example.parabbitmq.prodaja.repositories.OrderProductRepository;
import com.example.parabbitmq.prodaja.repositories.OrderRepository;
import com.example.parabbitmq.roba.data.ArticleWarehouse;
import com.example.parabbitmq.roba.data.Product;
import com.example.parabbitmq.roba.data.Reservation;
import com.example.parabbitmq.roba.data.Warehouse;
import com.example.parabbitmq.roba.repositories.ArticleWarehouseRepository;
import com.example.parabbitmq.roba.repositories.ProductRepository;
import com.example.parabbitmq.roba.repositories.ReservationRepository;
import com.example.parabbitmq.roba.repositories.WarehouseRepository;
import com.example.parabbitmq.prodaja.services.OrderService;
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
            Product product = new Product("product1","ml");
            Product product2 = new Product("product2","ml");
            productRepository.save(product);
            productRepository.save(product2);
           // double pricePerUnit = product.getPurchasePrice()*1.2;
            ArticleWarehouse articleWarehouse = new ArticleWarehouse(product,35.5);
            ArticleWarehouse articleWarehouse1 = new ArticleWarehouse(product2,35.5);
            articleWarehouseRepository.save(articleWarehouse);
            articleWarehouseRepository.save(articleWarehouse1);
            Warehouse warehouse = new Warehouse(1,1,articleWarehouse,5);
            Warehouse warehouse1 = new Warehouse(1,1,articleWarehouse1,5);
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
            //Invoice invoice = new Invoice(accounting,LocalDate.now());
            //invoiceRepository.save(invoice);
            Reservation reservation = new Reservation(product,5,o);
            reservationRepository.save(reservation);
            orderService.checkAccountings();
//Informacije o
//artiklima se na početku dobijaju iz servisa modula Roba, getAllProducts.
            /*log.info("Svi artikli");
            StringBuilder sb = new StringBuilder();
            sb.append("All products:\n");
            List<Product> products = productService.getAllProducts();
            for(Product p : products)
            {
                sb.append(" ");
                sb.append(p);
            }
            log.info(sb.toString());*/
            /*log.info("rezervacije za neki proizvod");
            Optional<Integer> quantity = reservationRepository.findTotalReservedQuantityByProductId((long) 1);
            log.info(String.valueOf(quantity));
            log.info(String.valueOf(quantity.get()));
*/

        });
    }
}
