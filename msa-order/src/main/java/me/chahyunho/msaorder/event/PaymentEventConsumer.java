package me.chahyunho.msaorder.event;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chahyunho.msaorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void init() {
        System.out.println("OrderEventConsumer initialized and ready to consume messages");
    }

    @KafkaListener(
            topics = "payment-complete",
            groupId = "order-api"
    )
    public void consumeOrderEvent(String orderId) {
        System.out.println("=== Order Event Consumed payment-complete===");
        System.out.println("Order ID: " + orderId);
        System.out.println("groupId: order-api" );
        System.out.println("Payment event processed successfully: " + orderId);

        orderService.updateOrder(Long.parseLong(orderId));
    }
}
