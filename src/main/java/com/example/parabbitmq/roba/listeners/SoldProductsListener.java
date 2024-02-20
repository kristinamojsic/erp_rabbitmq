package com.example.parabbitmq.roba.listeners;

import com.example.parabbitmq.messaging.SoldProductsMessage;
import com.example.parabbitmq.prodaja.data.Accounting;
import com.example.parabbitmq.prodaja.data.Invoice;
import com.example.parabbitmq.prodaja.data.Order;
import com.example.parabbitmq.prodaja.data.OrderProduct;
import com.example.parabbitmq.prodaja.repositories.OrderProductRepository;
import com.example.parabbitmq.roba.repositories.ReservationRepository;
import com.example.parabbitmq.roba.repositories.WarehouseRepository;
import com.example.parabbitmq.roba.data.Warehouse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.example.parabbitmq.RabbitMQConfigurator.SOLDPRODUCTS_SERVICE_QUEUE;

@Component
public class SoldProductsListener {
    @Autowired
    OrderProductRepository orderProductRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @RabbitListener(queues = SOLDPRODUCTS_SERVICE_QUEUE)
    private void processSoldProductsMessage(SoldProductsMessage soldProductsMessage)
    {
        Invoice invoice = soldProductsMessage.getInvoice();
        Accounting accounting = invoice.getAccounting();
        Order order = accounting.getOrder();
        StringBuilder sb = new StringBuilder();
        sb.append("Iz modula roba: " + "prodali su se proizvodi\n");

        List<OrderProduct> orderProducts = orderProductRepository.findOrderProducts(order.getId());
        for(OrderProduct orderProduct : orderProducts)
        {
            sb.append(orderProduct.getProduct().getId()).append(" u kolicini:")
                    .append(orderProduct.getQuantity());
        }

        //ukloniti iz rezervisanih i ukloniti iz magacina
        for(OrderProduct orderProduct : orderProducts)
        {
            Optional<Long> reservationIdOptional = reservationRepository.findReservationId(orderProduct.getProduct().getId(),orderProduct.getQuantity());
            List<Warehouse> warehouseStateList = warehouseRepository.findStateOfWarehousesForProductId(orderProduct.getProduct().getId());
            int remaining = orderProduct.getQuantity();
            if(!warehouseStateList.isEmpty())
            {
                for(Warehouse warehouseState : warehouseStateList)
                {
                    int warehouseQuantity = warehouseState.getQuantity();
                    int taken = 0;
                    if(remaining<=0) break;
                    int remainingWarehouseQuantity = warehouseQuantity - remaining;
                    if(remainingWarehouseQuantity <= 0)
                    {
                        taken = warehouseQuantity;
                        warehouseRepository.delete(warehouseState);
                    }
                    else
                    {
                        taken = remaining;
                        warehouseState.setQuantity(remainingWarehouseQuantity);
                        warehouseRepository.save(warehouseState);
                    }
                    remaining-=taken;
                }
            }
            if(reservationIdOptional.isPresent())
            {
                reservationRepository.deleteById(reservationIdOptional.get());
            }
        }

        System.out.println(sb);
    }
}
