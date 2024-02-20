package com.example.parabbitmq.services;

import com.example.parabbitmq.RabbitMQConfigurator;
import com.example.parabbitmq.data.*;
import com.example.parabbitmq.messaging.ReservationCancellation;
import com.example.parabbitmq.messaging.ReservationMessage;
import com.example.parabbitmq.messaging.SoldProductsMessage;
import com.example.parabbitmq.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private AccountingRepository accountingRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    public OrderService() {
    }

    @Transactional
    public void addOrder(Order order) {
        this.orderRepository.save(order);
        double totalPrice = 0.0;
        LocalDate dateOfPayment = LocalDate.now().plusDays(5);
        List<OrderProduct> productList = order.getProductList();


        for(OrderProduct orderProduct : productList) {
            int count = 0;
            double purchasePrice = 0.0;
            for(Warehouse w : warehouseRepository.findStateOfWarehousesForProductId(orderProduct.getProduct().getId())) {
                ++count;
                purchasePrice += w.getProduct().getPurchasePrice();
            }
            purchasePrice /= count;
            orderProduct.setPricePerUnit(purchasePrice+(purchasePrice*0.2));
            orderProduct.setTotalPrice((orderProduct.getPricePerUnit()+orderProduct.getPdv())*orderProduct.getQuantity());
            orderProduct.setOrder(order);
            this.orderProductRepository.save(orderProduct);
            totalPrice += orderProduct.getTotalPrice();
        }
        Accounting tmpAccounting = new Accounting(order,dateOfPayment,totalPrice);
        ReservationMessage reservationMessage = new ReservationMessage(productList,tmpAccounting);
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.ORDERS_TOPIC_EXCHANGE_NAME,
                "reservation.queue", reservationMessage);

    }

    public Invoice addInvoice(long accountingId,double totalPrice) throws Exception {
        Optional<Accounting> accountingOptional= accountingRepository.findById(accountingId);
        try {
            Accounting accounting = accountingOptional.get();
            if(accounting.getState()==1) throw new Exception("Order already paid");
            accounting.setState((short) 1);
            accountingRepository.save(accounting);
            LocalDate payDate = LocalDate.now();
            Invoice invoice = new Invoice(accounting,payDate);
            invoiceRepository.save(invoice);
            //poslati poruku prodata roba
            SoldProductsMessage soldProductsMessage = new SoldProductsMessage(invoice);
            rabbitTemplate.convertAndSend(RabbitMQConfigurator.SOLD_TOPIC_EXCHANGE_NAME,
                    "soldproducts.queue",soldProductsMessage);
            return invoice;
        }catch(Exception e) {
            throw new Exception("non-existing accounting id");
        }

    }

    public void checkAccountings() {
        LocalDate date = LocalDate.now();
        List<Accounting> accountings = accountingRepository.deadlinePassed(date);
        if(!accountings.isEmpty()){
            for(Accounting accounting : accountings){
                accounting.setState((short) 2);
                accountingRepository.save(accounting);
                long orderId = accounting.getOrder().getId();
                ReservationCancellation reservationCancellation = new ReservationCancellation(orderId);
                rabbitTemplate.convertAndSend(RabbitMQConfigurator.CANCELRESERVATION_TOPIC_EXCHANGE_NAME,
                        "cancelreservation.queue",reservationCancellation);

            }
        }
    }
}
