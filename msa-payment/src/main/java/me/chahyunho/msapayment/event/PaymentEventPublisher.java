package me.chahyunho.msapayment.event;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private static final String PAYMENT_CREATED = "payment-complete";

  public void publishPaymentCreated(Long orderId) {
    log.info("Kafka 메시지 전송 시도: {}", orderId);
    try {
      kafkaTemplate.send(PAYMENT_CREATED, orderId.toString())
          .addCallback(
              result -> System.out.println(
                  "Message sent successfully to topic: " + PAYMENT_CREATED + ", partition: " +
                      Objects.requireNonNull(result).getRecordMetadata().partition()),
              ex -> log.debug("Failed to send message to topic: {}", PAYMENT_CREATED, ex)
          );
    } catch (Exception e) {
      log.error("Error publishing order created event", e);
      throw new RuntimeException("Failed to publish order event", e);
    }
  }
}
