package me.chahyunho.msapayment.event;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chahyunho.msapayment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    @Autowired
    private PaymentService paymentService;

    @PostConstruct
    public void init() {
        System.out.println("OrderEventConsumer initialized and ready to consume messages");
    }

    @KafkaListener(
            topics = "order-event",
            groupId = "payment-api"
    )
    public void consumeOrderEvent(String orderId) {
        System.out.println("=== Order Event Consumed created===");
        System.out.println("Order ID: " + orderId);
        System.out.println("groupId: payment-api" );
        System.out.println("Order event processed successfully: " + orderId);

        paymentService.createPayment(orderId);
    }

    @KafkaListener(
        topics = "order-cancelled",
        groupId = "payment-api"
    )
    public void consumeOrderCancelled(String orderId) {
        System.out.println("=== Order Event Consumed cancel ===");
        System.out.println("Order ID: " + orderId);
        System.out.println("groupId: payment-api" );
        System.out.println("Order event processed successfully: " + orderId);

        paymentService.cancelPayment(orderId);
    }
}
